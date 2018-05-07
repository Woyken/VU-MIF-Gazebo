package lt.vu.mif.repository.repository.implementations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import lt.vu.mif.model.user.User_;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import lt.vu.mif.model.user.User;
import lt.vu.mif.model.user.UserTokenTuple;
import lt.vu.mif.repository.repository.interfaces.IUserRepository;
import lt.vu.mif.repository.utils.PersistenceUtils;

@Transactional
@Repository
public class UserRepository extends SimpleJpaRepository<User, Long> implements IUserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public UserRepository(EntityManager entityManager) {
        super(JpaEntityInformationSupport.getEntityInformation(User.class, entityManager),
            entityManager);
    }

    public User get(Long id) {
        return entityManager.find(User.class, id);
    }

    public void update(User user) {
        entityManager.merge(user);
    }

    @Transactional
    public void updateEmail(String currentEmail, String newEmail) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<User> criteria = builder.createCriteriaUpdate(User.class);
        Root<User> root = criteria.from(User.class);

        criteria.where(builder.equal(root.get(User_.email), currentEmail));
        criteria.set(root.get(User_.email), newEmail);
        entityManager.createQuery(criteria).executeUpdate();
    }

    public void changeUserPassword(String userEmail, String password) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<User> criteria = builder.createCriteriaUpdate(User.class);
        Root<User> root = criteria.from(User.class);

        criteria.where(builder.equal(root.get(User_.password), password));
        criteria.set(root.get(User_.email), password);
        entityManager.createQuery(criteria).executeUpdate();
    }

    public void blockUser(Long userId, boolean block) {
        Objects.requireNonNull(userId);

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<User> criteria = builder.createCriteriaUpdate(User.class);
        Root<User> root = criteria.from(User.class);

        criteria.set(root.get(User_.blocked), block);
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

    public UserTokenTuple getTokenCreationDate(String token) {
        Objects.requireNonNull(token);

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> criteria = builder.createQuery(Tuple.class);
        Root<User> root = criteria.from(User.class);

        Path<String> email = root.get(User_.email);
        Path<LocalDateTime> creationDate = root.get(User_.tokenCreationDate);

        criteria.where(builder.equal(root.get(User_.passwordToken), token));
        criteria.multiselect(email, creationDate);

        Tuple result = PersistenceUtils.uniqueResult(entityManager.createQuery(criteria));
        return result == null ? null
            : new UserTokenTuple(result.get(email), result.get(creationDate), token);
    }
}