package lt.vu.mif.Repository;

import lt.vu.mif.Entity.Product;
import lt.vu.mif.Entity.Product_;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

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
}