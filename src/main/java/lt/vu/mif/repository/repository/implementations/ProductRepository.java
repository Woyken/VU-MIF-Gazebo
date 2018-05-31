package lt.vu.mif.repository.repository.implementations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lt.vu.mif.Logging.Logged;
import lt.vu.mif.model.product.Category;
import lt.vu.mif.model.product.Product;
import lt.vu.mif.model.product.Product_;
import lt.vu.mif.repository.repository.interfaces.IProductRepository;
import lt.vu.mif.repository.utils.PersistenceUtils;
import lt.vu.mif.utils.search.ProductSearch;
import lt.vu.mif.utils.search.ProductSortEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Logged
@Transactional
@Repository
public class ProductRepository extends SimpleJpaRepository<Product, Long> implements
    IProductRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Integer getProductVersion(Long productId) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Integer> criteria = builder.createQuery(Integer.class);
        Root<Product> root = criteria.from(Product.class);

        criteria.where(builder.equal(root.get(Product_.id), productId));
        criteria.select(root.get(Product_.version));

        return PersistenceUtils.uniqueResult(entityManager.createQuery(criteria));
    }

    @Override
    public Long getIdBySku(String skuCode) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Product> root = criteria.from(Product.class);

        criteria.where(builder.equal(root.get(Product_.sku), skuCode));
        criteria.select(root.get(Product_.id));

        try {
            return entityManager.createQuery(criteria).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Autowired
    public ProductRepository(EntityManager entityManager) {
        super(JpaEntityInformationSupport.getEntityInformation(Product.class, entityManager),
            entityManager);
    }

    @Override
    public Product update(Product entity) {
        return entityManager.merge(entity);
    }

    public void updateAll(List<Product> products) {
        for (Product product : products) {
            entityManager.merge(product);
            entityManager.flush();
        }
    }

    public void deleteAll(List<Long> productIds) {
        if (CollectionUtils.isEmpty(productIds)) {
            return;
        }

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Product> criteria = builder.createCriteriaUpdate(Product.class);
        Root<Product> root = criteria.from(Product.class);

        criteria.where(root.get(Product_.id).in(productIds));
        criteria.set(root.get(Product_.deleted), true);

        entityManager.createQuery(criteria).executeUpdate();
    }

    public Product get(Long id) {
        return entityManager.find(Product.class, id);
    }

    public List<Product> get(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteria = builder.createQuery(Product.class);
        Root<Product> root = criteria.from(Product.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(root.get(Product_.id).in(ids));
        predicates.add(builder.isFalse(root.get(Product_.deleted)));

        criteria.where(PersistenceUtils.toArray(predicates));
        criteria.select(root);

        return entityManager.createQuery(criteria).getResultList();
    }

    public List<Product> getAllByCategory(Category category) {
        Objects.requireNonNull(category);

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteria = builder.createQuery(Product.class);
        Root<Product> root = criteria.from(Product.class);

        criteria.where(builder.equal(root.get(Product_.category), category));
        criteria.select(root);

        return entityManager.createQuery(criteria).getResultList();
    }

    private List<Predicate> buildSearchPredicates(ProductSearch search, Root<Product> root,
        CriteriaBuilder builder) {

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.isFalse(root.get(Product_.deleted)));

        if (StringUtils.isNotBlank(search.getTitle())) {
            predicates.add(builder
                .like(builder.lower(root.get(Product_.title)), "%" + search.getTitle().toLowerCase(
                    Locale.getDefault()) + "%"));
        }

        if (search.getMinPrice() != null) {
            predicates
                .add(builder.greaterThanOrEqualTo(root.get(Product_.price), search.getMinPrice()));
        }

        if (search.getMaxPrice() != null) {
            predicates
                .add(builder.lessThanOrEqualTo(root.get(Product_.price), search.getMaxPrice()));
        }

        if (!CollectionUtils.isEmpty(search.getCategories())) {
            //TODO is this good??
            predicates.add(root.get(Product_.category).in(search.getCategories()));
        }

        if (!search.isIncludeDeleted()) {
            predicates
                .add(builder.isFalse(root.get(Product_.deleted)));
        }

        return predicates;
    }

    public Page<Product> getProductsPage(ProductSearch productSearch, int activePage,
        int pageSize) {
        PageRequest pageRequest = PageRequest.of(activePage, pageSize);

        Specification<Product> specification = (root, criteriaQuery, criteriaBuilder) -> {
            if (productSearch.getSortBy() == ProductSortEnum.CREATE_DATE) {
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get(Product_.creationDate)));
            } else if (productSearch.getSortBy() == ProductSortEnum.PRICE_DESCENDING) {
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get(Product_.price)));
            } else if (productSearch.getSortBy() == ProductSortEnum.PRICE_ASCENDING) {
                criteriaQuery.orderBy(criteriaBuilder.asc(root.get(Product_.price)));
            }
            return criteriaBuilder.and(PersistenceUtils
                .toArray(buildSearchPredicates(productSearch, root, criteriaBuilder)));
        };

        return findAll(specification, pageRequest);
    }
}