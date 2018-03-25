package lt.vu.mif.Repository;

import lt.vu.mif.Entity.Product;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public class ProductRepository extends BaseRepository<Product> {
}