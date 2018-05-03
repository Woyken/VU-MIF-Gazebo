package lt.vu.mif.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lt.vu.mif.Entity.OrderProduct;
import lt.vu.mif.Entity.OrderProduct_;
import lt.vu.mif.Entity.Order_;
import lt.vu.mif.Entity.User_;
import lt.vu.mif.Utils.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class BoughProductRepository extends SimpleJpaRepository<OrderProduct, Long> {
    @Autowired
    public BoughProductRepository(EntityManager entityManager) {
        super(JpaEntityInformationSupport.getEntityInformation(OrderProduct.class, entityManager),
            entityManager);
    }

    public Page<OrderProduct> getBoughtProductsPage(Paging paging, Long userId) {
        PageRequest pageRequest = PageRequest.of(paging.getActivePage(), paging.getPageSize());
        Specification<OrderProduct> specification = Specification.where((root, criteriaQuery, builder) -> builder.equal(root.get(OrderProduct_.order).get(Order_.user).get(User_.id), userId));

        return findAll(specification, pageRequest);
    }
}