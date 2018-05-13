package lt.vu.mif.repository.repository.implementations;

import lt.vu.mif.model.order.OrderRating;
import lt.vu.mif.repository.repository.interfaces.IOrderRatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Transactional
@Repository
public class OrderRatingRepository extends SimpleJpaRepository<OrderRating, Long> implements IOrderRatingRepository{

    @Autowired
    public OrderRatingRepository(EntityManager entityManager) {
        super(JpaEntityInformationSupport.getEntityInformation(OrderRating.class, entityManager),
                entityManager);
    }

    @Override
    public OrderRating save(OrderRating rating) {
        return super.save(rating);
    }

    @Override
    public OrderRating get(Long id) {
        return super.getOne(id);
    }
}
