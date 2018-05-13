package lt.vu.mif.repository.repository.implementations;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import lt.vu.mif.model.order.Rating;
import lt.vu.mif.repository.repository.interfaces.IRatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public class RatingRepository extends SimpleJpaRepository<Rating, Long> implements
    IRatingRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public RatingRepository(EntityManager entityManager) {
        super(JpaEntityInformationSupport.getEntityInformation(Rating.class, entityManager),
            entityManager);
    }
}