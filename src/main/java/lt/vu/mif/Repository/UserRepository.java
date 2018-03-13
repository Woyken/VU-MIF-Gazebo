package lt.vu.mif.Repository;

import lt.vu.mif.Entity.User;
import lt.vu.mif.Entity.User_;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.Objects;

@Transactional
@Repository
public class UserRepository extends BaseRepository<User> {

    public boolean checkIfUserExists(String email) {
        Objects.requireNonNull(email);

        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<User> root = criteria.from(User.class);

        criteria.where(builder.equal(root.get(User_.email), email));
        criteria.select(builder.count(root));

        return getEntityManager().createQuery(criteria).getSingleResult() > 0;
    }
}
