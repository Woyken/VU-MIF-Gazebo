package lt.vu.mif.repository.repository.implementations;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import lt.vu.mif.model.product.Category;
import lt.vu.mif.repository.repository.interfaces.ICategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public class CategoryRepository extends SimpleJpaRepository<Category, Long> implements
    ICategoryRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public CategoryRepository(EntityManager entityManager) {
        super(JpaEntityInformationSupport
                .getEntityInformation(Category.class, entityManager),
            entityManager);
    }

    @Override
    public Category get(Long id) {
        return entityManager.find(Category.class, id);
    }
}