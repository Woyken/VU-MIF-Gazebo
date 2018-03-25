package lt.vu.mif.Repository;

import lt.vu.mif.Entity.Product;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
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
}