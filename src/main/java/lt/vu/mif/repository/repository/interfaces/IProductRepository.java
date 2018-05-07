package lt.vu.mif.repository.repository.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;

import lt.vu.mif.bl.search.ProductSearch;
import lt.vu.mif.model.product.Product;

public interface IProductRepository {

    Product get(Long id);

    List<Product> get(List<Long> ids);

    List<Product> findAll();

    Page<Product> getProductsPage(ProductSearch productSearch, int activePage, int pageSize);

    void deleteById(Long id);

    <S extends Product> S save(S entity);

    <S extends Product> List<S> saveAll(Iterable<S> entities);
}
