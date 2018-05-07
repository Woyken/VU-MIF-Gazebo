package lt.vu.mif.repository.repository.implementations;

import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import lt.vu.mif.model.order.Order;
import lt.vu.mif.model.order.Order_;
import lt.vu.mif.model.user.User_;
import lt.vu.mif.repository.repository.interfaces.IOrderRepository;

@Transactional
@Repository
public class OrderRepository extends SimpleJpaRepository<Order, Long> implements
    IOrderRepository {

    @Autowired
    public OrderRepository(EntityManager entityManager) {
        super(JpaEntityInformationSupport.getEntityInformation(Order.class, entityManager),
            entityManager);
    }

    @Override
    public void saveOrder(Order order) {
        save(order);
    }

    public List<Order> getAllUserOrders(Long userId) {
        Objects.requireNonNull(userId);

        Specification<Order> specification = Specification.where(
            (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                .equal(root.get(Order_.user).get(User_.id), userId));
        return findAll(specification);
    }
}
