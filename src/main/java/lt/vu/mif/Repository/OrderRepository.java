package lt.vu.mif.Repository;

import java.util.List;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import lt.vu.mif.Entity.Order;
import lt.vu.mif.Entity.Order_;
import lt.vu.mif.Entity.User;
import lt.vu.mif.Entity.User_;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public class OrderRepository extends SimpleJpaRepository<Order, Long> {


    @Autowired
    public OrderRepository(EntityManager entityManager) {
        super(JpaEntityInformationSupport.getEntityInformation(Order.class, entityManager),
            entityManager);
    }

    public List<Order> getAllUserOrders(Long userId) {
        Objects.requireNonNull(userId);

        Specification<Order> specification = Specification.where(
            (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                .equal(root.get(Order_.user).get(User_.id), userId));
        return findAll(specification);
    }

    public List<Order> getAll() {

        return findAll();
    }
}
