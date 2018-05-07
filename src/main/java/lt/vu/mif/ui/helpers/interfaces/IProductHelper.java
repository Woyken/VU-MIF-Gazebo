package lt.vu.mif.ui.helpers.interfaces;

import java.io.InputStream;
import java.util.List;

import lt.vu.mif.model.order.OrderProduct;
import lt.vu.mif.ui.view.BoughtProductView;
import lt.vu.mif.ui.view.CartProductView;
import org.springframework.data.domain.Page;

import lt.vu.mif.bl.search.ProductSearch;
import lt.vu.mif.model.product.Product;
import lt.vu.mif.ui.view.ProductView;

public interface IProductHelper {
    void createNewProduct(ProductView newProduct);

    ProductView getProduct(Long productId);

    void importProducts(InputStream inputStream);

    Page<ProductView> getProductsPage(int activePage, int pageSize, ProductSearch search);

    void deleteById(Long id);

    void saveAll(List<Product> productList);

    CartProductView getCartProductView(Long productId);

    Page<BoughtProductView> getBoughtProductsPage(int activePage, int pageSize, Long userId);
}
