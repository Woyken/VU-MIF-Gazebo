package lt.vu.mif.ui.helpers.interfaces;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lt.vu.mif.excel.ImportResult;
import lt.vu.mif.model.product.Product;
import lt.vu.mif.ui.view.BoughtProductView;
import lt.vu.mif.ui.view.CartItemView;
import lt.vu.mif.ui.view.CartView;
import lt.vu.mif.ui.view.ProductSearchView;
import lt.vu.mif.ui.view.ProductView;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

public interface IProductHelper {

    void createNewProduct(ProductView newProduct);

    ProductView getProduct(Long productId);

    CompletableFuture<ImportResult> importProducts(InputStream inputStream);

    @Transactional
    CartView setCurrentUserCart(CartView cart);

    @Transactional
    void currentUserClearCart();

    Page<ProductView> getProductsPage(int activePage, int pageSize, ProductSearchView search);

    void deleteById(Long id);

    void deleteMultipleByIds(List<Long> productIds);

    void saveAll(List<Product> productList);

    CartItemView getCartProductView(Long productId);

    CartView getCurrentUserCart();

    Page<BoughtProductView> getBoughtProductsPage(int activePage, int pageSize, Long userId);

    Page<BoughtProductView> getCurrentUserBoughtProductsPage(int activePage, int pageSize);

    BigDecimal getProductsSum(List<BoughtProductView> productViews);

    ProductView getProductViewFromNavigationQuery();

    void update(ProductView view);

    Integer getProductVersion(Long productId);

    boolean checkIfProductExists(String skuCode);
}
