package lt.vu.mif.repository.repository.implementations;

import java.util.List;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import lt.vu.mif.model.order.Order;
import lt.vu.mif.model.order.Order_;
import lt.vu.mif.model.user.User_;
import lt.vu.mif.repository.repository.interfaces.IOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public class OrderRepository extends SimpleJpaRepository<Order, Long> implements
    IOrderRepository {

    @Autowired
    public OrderRepository(EntityManager entityManager) {
        super(JpaEntityInformationSupport.getEntityInformation(Order.class, entityManager),
            entityManager);
    }

    public List<Order> getAllUserOrders(String email) {
        Objects.requireNonNull(email);

        Specification<Order> specification = Specification.where(
            (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                .equal(root.get(Order_.user).get(User_.email), email));
        return findAll(specification);
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
