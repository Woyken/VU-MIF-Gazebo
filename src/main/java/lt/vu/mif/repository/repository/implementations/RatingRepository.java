package lt.vu.mif.repository.repository.implementations;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lt.vu.mif.Logging.Logged;
import lt.vu.mif.model.order.Rating;
import lt.vu.mif.repository.repository.interfaces.IRatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Logged
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