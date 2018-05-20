package lt.vu.mif.repository.repository.implementations;

import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import lt.vu.mif.model.product.Category;
import lt.vu.mif.model.product.Category_;
import lt.vu.mif.repository.repository.interfaces.ICategoryRepository;
import lt.vu.mif.repository.utils.PersistenceUtils;
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

    @Override
    public Category getCategoryByName(String name) {
        Objects.requireNonNull(name);

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Category> criteria = builder.createQuery(Category.class);
        Root<Category> root = criteria.from(Category.class);

        criteria.select(root);
        criteria.where(builder.equal(root.get(Category_.name), name));

        return PersistenceUtils.uniqueResult(entityManager.createQuery(criteria));
    }
}