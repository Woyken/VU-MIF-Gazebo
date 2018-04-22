package lt.vu.mif.Repository;

import java.util.List;
import java.util.Objects;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import lt.vu.mif.Entity.User;
import lt.vu.mif.Entity.User_;
import lt.vu.mif.Utils.PersistenceUtils;

@Transactional
@Repository
public class UserRepository extends BaseRepository<User> {

    public List<User> getAll() {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> root = criteria.from(User.class);

        criteria.select(root);

        return getEntityManager().createQuery(criteria).getResultList();
    }

    public User get(Long id) {
        Objects.requireNonNull(id);

        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> root = criteria.from(User.class);

        criteria.select(root);
        criteria.where(builder.equal(root.get(User_.id), id));

        return PersistenceUtils.uniqueResult(getEntityManager().createQuery(criteria));
    }

    public boolean checkIfUserExists(String email) {
        Objects.requireNonNull(email);

        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<User> root = criteria.from(User.class);

        criteria.where(builder.equal(root.get(User_.email), email));
        criteria.select(builder.count(root));

        return getEntityManager().createQuery(criteria).getSingleResult() > 0;
    }

    public User getUserByEmail(String email) {
        Objects.requireNonNull(email);

        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> root = criteria.from(User.class);

        criteria.select(root);
        criteria.where(builder.equal(root.get(User_.email), email));

        return PersistenceUtils.uniqueResult(getEntityManager().createQuery(criteria));
    }

    public User getUserByToken(String token) {
        Objects.requireNonNull(token);

        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> root = criteria.from(User.class);

        criteria.where(builder.equal(root.get(User_.passwordToken), token));
        criteria.select(root);

        return PersistenceUtils.uniqueResult(getEntityManager().createQuery(criteria));
    }
}
