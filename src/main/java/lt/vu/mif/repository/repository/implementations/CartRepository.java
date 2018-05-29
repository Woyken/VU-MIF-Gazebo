package lt.vu.mif.repository.repository.implementations;

import lt.vu.mif.Logging.Logged;
import lt.vu.mif.model.product.Cart;
import lt.vu.mif.model.product.Cart_;
import lt.vu.mif.model.user.User_;
import lt.vu.mif.repository.repository.interfaces.ICartRepository;
import lt.vu.mif.repository.utils.PersistenceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Objects;

@Logged
@Transactional
@Repository
public class CartRepository extends SimpleJpaRepository<Cart, Long> implements ICartRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public CartRepository(EntityManager entityManager) {
        super(JpaEntityInformationSupport.getEntityInformation(Cart.class, entityManager),
                entityManager);
    }

    @Override
    public Cart get(Long id) {
        return entityManager.find(Cart.class, id);
    }

    @Override
    public Cart update(Cart entity) {

        return entityManager.merge(entity);
    }

    @Override
    public void updateAll(List<Cart> carts) {
        for (Cart entity : carts) {
            entityManager.merge(entity);
        }
    }

    @Override
    public Cart getByUserId(Long userId) {
        Objects.requireNonNull(userId);

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Cart> criteria = builder.createQuery(Cart.class);
        Root<Cart> root = criteria.from(Cart.class);

        criteria.select(root);
        criteria.where(builder.equal(root.get(Cart_.user).get(User_.id), userId));

        return PersistenceUtils.uniqueResult(entityManager.createQuery(criteria));
    }
}
