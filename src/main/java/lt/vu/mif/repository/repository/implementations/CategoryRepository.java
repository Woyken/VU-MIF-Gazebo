package lt.vu.mif.repository.repository.implementations;

import lt.vu.mif.Logging.Logged;
import lt.vu.mif.model.product.Category;
import lt.vu.mif.model.product.Category_;
import lt.vu.mif.repository.repository.interfaces.ICategoryRepository;
import lt.vu.mif.repository.utils.PersistenceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Objects;

@Logged
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

    public Category getRootCategory() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Category> criteria = builder.createQuery(Category.class);
        Root<Category> root = criteria.from(Category.class);

        criteria.select(root);
        criteria.where(builder.isNull(root.get(Category_.parentCategory)));

        return PersistenceUtils.uniqueResult(entityManager.createQuery(criteria));
    }

    @Override
    public Category update(Category entity) {
        return entityManager.merge(entity);
    }

    @Override
    public void updateAll(List<Category> entities) {
        for (Category entity : entities) {
            entityManager.merge(entity);
        }
    }

    public void delete(Category entity) {
        entityManager.remove(entity);
    }
}