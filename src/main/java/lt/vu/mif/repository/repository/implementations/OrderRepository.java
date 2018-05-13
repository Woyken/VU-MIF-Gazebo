package lt.vu.mif.repository.repository.implementations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import lt.vu.mif.model.order.Order;
import lt.vu.mif.model.order.Order_;
import lt.vu.mif.model.user.User_;
import lt.vu.mif.repository.repository.interfaces.IOrderRepository;
import lt.vu.mif.repository.utils.PersistenceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

@Transactional
@Repository
public class OrderRepository extends SimpleJpaRepository<Order, Long> implements
    IOrderRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public OrderRepository(EntityManager entityManager) {
        super(JpaEntityInformationSupport.getEntityInformation(Order.class, entityManager),
            entityManager);
    }

    @Override
    public void updateAll(List<Order> orders) {
        for (Order order : orders) {
            entityManager.merge(order);
        }
    }

    @Override
    public List<Order> getOrders(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteria = builder.createQuery(Order.class);
        Root<Order> root = criteria.from(Order.class);

        criteria.where(root.get(Order_.id).in(ids));

        return entityManager.createQuery(criteria).getResultList();
    }

    public List<Order> getAllUserOrders(String email) {
        Objects.requireNonNull(email);

        Specification<Order> specification = Specification.where(
            (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                .equal(root.get(Order_.user).get(User_.email), email));
        return findAll(specification);
    }

    public List<Order> getCurrentUserOrdersToRate(String currentUserEmail) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteria = builder.createQuery(Order.class);
        Root<Order> root = criteria.from(Order.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.isFalse(root.get(Order_.rated)));
        predicates.add(builder.equal(root.join(Order_.user).get(User_.email), currentUserEmail));

        criteria.where(PersistenceUtils.toArray(predicates));

        return entityManager.createQuery(criteria).getResultList();
    }


    @Override
    public Order save(Order order) {
        return super.save(order);
    }

    @Override
    public Order get(Long id) {
        return super.getOne(id);
    }


}
