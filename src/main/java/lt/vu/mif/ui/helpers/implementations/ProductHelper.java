package lt.vu.mif.ui.helpers.implementations;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import javax.transaction.Transactional;
import lt.vu.mif.authentication.UserService;
import lt.vu.mif.excel.ExcelProduct;
import lt.vu.mif.excel.ProductExcelReader;
import lt.vu.mif.model.product.Product;
import lt.vu.mif.repository.repository.interfaces.IBoughtProductRepository;
import lt.vu.mif.repository.repository.interfaces.IProductRepository;
import lt.vu.mif.ui.helpers.interfaces.IProductHelper;
import lt.vu.mif.ui.mappers.implementations.BoughtProductMapper;
import lt.vu.mif.ui.mappers.implementations.ProductMapper;
import lt.vu.mif.ui.view.BoughtProductView;
import lt.vu.mif.ui.view.CartProductView;
import lt.vu.mif.ui.view.ProductView;
import lt.vu.mif.utils.interfaces.IProductParser;
import lt.vu.mif.utils.search.ProductSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Transactional
@Component
public class ProductHelper implements IProductHelper {

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private BoughtProductMapper boughtProductMapper;
    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private ProductExcelReader productExcelReader;
    @Autowired
    private IProductParser productParser;
    @Autowired
    private IBoughtProductRepository boughtProductRepository;
    @Autowired
    private UserService userService;

    @Override
    public Page<BoughtProductView> getBoughtProductsPage(int activePage, int pageSize,
        Long userId) {
        return boughtProductRepository.getBoughtProductsPage(activePage, pageSize, userId)
            .map(boughtProductMapper::toView);
    }

    @Override
    public Page<BoughtProductView> getCurrentUserBoughtProductsPage(int activePage, int pageSize) {
        Long currentUserId = userService.getLoggedUser().getId();
        return getBoughtProductsPage(activePage, pageSize, currentUserId);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public void saveAll(List<Product> productList) {
        productRepository.saveAll(productList);
    }

    @Override
    public CartProductView getCartProductView(Long productId) {
        return new CartProductView(productMapper.toView(productRepository.get(productId)));
    }

    @Override
    public Page<ProductView> getProductsPage(int activePage, int pageSize, ProductSearch search) {
        return productRepository.getProductsPage(search, activePage, pageSize)
            .map(productMapper::toView);
    }

    @Override
    public void createNewProduct(ProductView newProduct) {
        Product product = productMapper.toEntity(newProduct);
        productRepository.save(product);
    }

    @Override
    public ProductView getProduct(Long productId) {
        return productMapper.toView(productRepository.get(productId));
    }

    @Override
    public CompletableFuture<Void> importProducts(InputStream inputStream) {
        CompletionStage<List<ExcelProduct>> productPromise = productExcelReader
            .readFile(inputStream);
        return productPromise.thenAccept((List<ExcelProduct> products) -> {
            List<Product> productsToSave = productParser.parseProducts(products);
            saveAll(productsToSave);
        }).toCompletableFuture();
    }

    @Override
    public BigDecimal getProductsSum(List<BoughtProductView> productViews) {
        BigDecimal totalSum = new BigDecimal(0L);

        for (BoughtProductView productView : productViews) {
            if (productView.getPrice() != null) {
                totalSum = totalSum.add(productView.getPrice().multiply(new BigDecimal(productView.getQuantity())));
            }
        }

        return totalSum;
    }
}
