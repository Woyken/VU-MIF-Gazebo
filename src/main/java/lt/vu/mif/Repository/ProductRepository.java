package lt.vu.mif.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import lt.vu.mif.Entity.Product;
import lt.vu.mif.Entity.Product_;
import lt.vu.mif.Search.ProductSearch;
import lt.vu.mif.Utils.Paging;
import lt.vu.mif.Utils.PersistenceUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

@Transactional
@Repository
public class ProductRepository extends SimpleJpaRepository<Product, Long> {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public ProductRepository(EntityManager entityManager) {
        super(JpaEntityInformationSupport.getEntityInformation(Product.class, entityManager), entityManager);
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

        criteria.where(root.get(Product_.id).in(ids));
        criteria.select(root);

        return entityManager.createQuery(criteria).getResultList();
    }

    public List<Product> getAll() {
        return findAll();
    }

    public List<Product> findProducts(ProductSearch search) {
        Objects.requireNonNull(search);

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteria = builder.createQuery(Product.class);
        Root<Product> root = criteria.from(Product.class);

        criteria.where(PersistenceUtils.toArray(buildSearchPredicates(search, root, builder)));
        criteria.select(root);

        return entityManager.createQuery(criteria).getResultList();
    }

    private List<Predicate> buildSearchPredicates(ProductSearch search, Root<Product> root, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.isNotBlank(search.getTitle())) {
            predicates.add(builder.like(root.get(Product_.title), "%" + search.getTitle() + "%"));
        }

        if (search.getMinPrice() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get(Product_.price), search.getMinPrice()));
        }

        if (search.getMaxPrice() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get(Product_.price), search.getMaxPrice()));
        }

        return predicates;
    }

    public Page<Product> getProductsPage(ProductSearch productSearch, Paging paging) {
        PageRequest pageRequest = PageRequest.of(paging.getActivePage(), paging.getPageSize());

        Specification<Product> specification = (root, criteriaQuery, criteriaBuilder) ->
            criteriaBuilder.and(PersistenceUtils.toArray(buildSearchPredicates(productSearch, root, criteriaBuilder)));

        return findAll(specification, pageRequest);
    }
}