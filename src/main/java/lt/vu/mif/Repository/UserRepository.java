package lt.vu.mif.Repository;

import java.util.List;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import lt.vu.mif.Entity.User;
import lt.vu.mif.Entity.User_;
import lt.vu.mif.Utils.Paging;
import lt.vu.mif.Utils.PersistenceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public class UserRepository extends SimpleJpaRepository<User, Long> {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public UserRepository(EntityManager entityManager) {
        super(JpaEntityInformationSupport.getEntityInformation(User.class, entityManager), entityManager);
    }

    public List<User> getAll() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> root = criteria.from(User.class);

        criteria.select(root);

        return entityManager.createQuery(criteria).getResultList();
    }

    public Page<User> getUsersPage(Paging paging) {
        return findAll(PageRequest.of(paging.getActivePage(), paging.getPageSize()));
    }

    public void blockUser(Long userId, boolean block) {
        Objects.requireNonNull(userId);

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<User> criteria = builder.createCriteriaUpdate(User.class);
        Root<User> root = criteria.from(User.class);

        criteria.set(root.get(User_.blocked),  block);
        criteria.where(builder.equal(root.get(User_.id), userId));

        entityManager.createQuery(criteria).executeUpdate();
    }

    public boolean checkIfUserExists(String email) {
        Objects.requireNonNull(email);

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<User> root = criteria.from(User.class);

        criteria.where(builder.equal(root.get(User_.email), email));
        criteria.select(builder.count(root));

        return entityManager.createQuery(criteria).getSingleResult() > 0;
    }

    public User getUserByEmail(String email) {
        Objects.requireNonNull(email);

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> root = criteria.from(User.class);

        criteria.select(root);
        criteria.where(builder.equal(root.get(User_.email), email));

        return PersistenceUtils.uniqueResult(entityManager.createQuery(criteria));
    }

    public User getUserByToken(String token) {
        Objects.requireNonNull(token);

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> root = criteria.from(User.class);

        criteria.where(builder.equal(root.get(User_.passwordToken), token));
        criteria.select(root);

        return PersistenceUtils.uniqueResult(entityManager.createQuery(criteria));
    }

    public void update(User user) {
        entityManager.merge(user);
    }
}
