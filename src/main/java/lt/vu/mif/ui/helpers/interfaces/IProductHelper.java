package lt.vu.mif.ui.helpers.interfaces;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lt.vu.mif.model.product.Product;
import lt.vu.mif.ui.view.BoughtProductView;
import lt.vu.mif.ui.view.CartProductView;
import lt.vu.mif.ui.view.ProductView;
import lt.vu.mif.utils.search.ProductSearch;
import org.springframework.data.domain.Page;

public interface IProductHelper {

    void createNewProduct(ProductView newProduct);

    ProductView getProduct(Long productId);

    CompletableFuture<Void> importProducts(InputStream inputStream);

    Page<ProductView> getProductsPage(int activePage, int pageSize, ProductSearch search);

    void deleteById(Long id);

    void deleteMultipleByIds(List<Long> productIds);

    void saveAll(List<Product> productList);

    CartProductView getCartProductView(Long productId);

    Page<BoughtProductView> getBoughtProductsPage(int activePage, int pageSize, Long userId);

    Page<BoughtProductView> getCurrentUserBoughtProductsPage(int activePage, int pageSize);

    BigDecimal getProductsSum(List<BoughtProductView> productViews);

    ProductView getProductViewFromNavigationQuery();

    void update(ProductView view);
}
