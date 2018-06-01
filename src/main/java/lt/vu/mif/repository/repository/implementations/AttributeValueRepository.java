package lt.vu.mif.repository.repository.implementations;

import javax.persistence.EntityManager;
import lt.vu.mif.Logging.Logged;
import lt.vu.mif.model.product.CategoryAttributeValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Logged
@Transactional
@Repository
public class AttributeValueRepository extends SimpleJpaRepository<CategoryAttributeValue, Long> {
    @Autowired
    public AttributeValueRepository(EntityManager entityManager) {
        super(JpaEntityInformationSupport.getEntityInformation(CategoryAttributeValue.class, entityManager),
            entityManager);
    }
}