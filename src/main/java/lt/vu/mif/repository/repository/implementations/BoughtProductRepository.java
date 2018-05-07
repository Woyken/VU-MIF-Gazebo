package lt.vu.mif.repository.repository.implementations;

import javax.persistence.EntityManager;
import lt.vu.mif.model.order.OrderProduct;
import lt.vu.mif.model.order.OrderProduct_;
import lt.vu.mif.model.order.Order_;
import lt.vu.mif.model.user.User_;
import lt.vu.mif.repository.repository.interfaces.IBoughtProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class BoughtProductRepository extends SimpleJpaRepository<OrderProduct, Long> implements
    IBoughtProductRepository {

    @Autowired
    public BoughtProductRepository(EntityManager entityManager) {
        super(JpaEntityInformationSupport.getEntityInformation(OrderProduct.class, entityManager),
            entityManager);
    }

    public Page<OrderProduct> getBoughtProductsPage(int activePage, int pageSize, Long userId) {
        PageRequest pageRequest = PageRequest.of(activePage, pageSize);
        Specification<OrderProduct> specification = Specification.where(
            (root, criteriaQuery, builder) -> builder
                .equal(root.get(OrderProduct_.order).get(Order_.user).get(User_.id), userId));

        return findAll(specification, pageRequest);
    }
}