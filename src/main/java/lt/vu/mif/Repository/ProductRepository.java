package lt.vu.mif.Repository;

import lt.vu.mif.Entity.Product;
import lt.vu.mif.Entity.Product_;
import lt.vu.mif.Search.ProductSearch;
import lt.vu.mif.Utils.PersistenceUtils;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Transactional
@Repository
public class ProductRepository extends BaseRepository<Product> {

    public List<Product> getAll() {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<Product> criteria = builder.createQuery(Product.class);
        Root<Product> root = criteria.from(Product.class);

        criteria.select(root);

        return getEntityManager().createQuery(criteria).getResultList();
    }

    public List<Long> getIds() {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Product> root = criteria.from(Product.class);

        criteria.select(root.get(Product_.id));
      
        return getEntityManager().createQuery(criteria).getResultList();
    }

    public List<Product> getByTitle(String title) {
        Objects.requireNonNull(title);

        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<Product> criteria = builder.createQuery(Product.class);
        Root<Product> root = criteria.from(Product.class);

        criteria.where(builder.like(root.get(Product_.title), "%" + title + "%"));
        criteria.select(root);

        return getEntityManager().createQuery(criteria).getResultList();
    }

    public List<Product> get(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }

        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<Product> criteria = builder.createQuery(Product.class);
        Root<Product> root = criteria.from(Product.class);

        criteria.where(root.get(Product_.id).in(ids));
        criteria.select(root);

        return getEntityManager().createQuery(criteria).getResultList();
    }

    public List<Product> findProducts(ProductSearch search) {
        Objects.requireNonNull(search);

        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<Product> criteria = builder.createQuery(Product.class);
        Root<Product> root = criteria.from(Product.class);

        criteria.where(PersistenceUtils.toArray(buildSearchPredicates(search, root, builder)));
        criteria.select(root);

        return getEntityManager().createQuery(criteria).getResultList();
    }

    private List<Predicate> buildSearchPredicates(ProductSearch search, Root<Product> root, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();

        if (search.getMinPrice() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get(Product_.price), search.getMinPrice()));
        }

        if (search.getMaxPrice() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get(Product_.price), search.getMaxPrice()));
        }

        return predicates;
    }
}