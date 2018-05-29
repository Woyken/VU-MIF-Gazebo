package lt.vu.mif.repository.repository.interfaces;

import lt.vu.mif.model.product.Category;
import lt.vu.mif.model.product.Product;
import lt.vu.mif.utils.search.ProductSearch;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IProductRepository {

    Product get(Long id);

    List<Product> get(List<Long> ids);

    List<Product> findAll();

    List<Product> getAllByCategory(Category category);

    Page<Product> getProductsPage(ProductSearch productSearch, int activePage, int pageSize);

    void deleteById(Long id);

    void deleteAll(List<Long> productIds);

    <S extends Product> S save(S entity);

    <S extends Product> List<S> saveAll(Iterable<S> entities);

    void update(Product entity);

    void updateAll(List<Product> products);

    boolean checkIfProductExists(String skuCode);
}
