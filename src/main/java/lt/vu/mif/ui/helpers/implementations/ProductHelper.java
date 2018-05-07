package lt.vu.mif.ui.helpers.implementations;

import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import javax.transaction.Transactional;
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

    public Page<BoughtProductView> getBoughtProductsPage(int activePage, int pageSize,
        Long userId) {
        return boughtProductRepository.getBoughtProductsPage(activePage, pageSize, userId)
            .map(boughtProductMapper::toView);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public void saveAll(List<Product> productList) {
        productRepository.saveAll(productList);
    }

    public CartProductView getCartProductView(Long productId) {
        return new CartProductView(productMapper.toView(productRepository.get(productId)));
    }

    public Page<ProductView> getProductsPage(int activePage, int pageSize, ProductSearch search) {
        return productRepository.getProductsPage(search, activePage, pageSize)
            .map(productMapper::toView);
    }

    public void createNewProduct(ProductView newProduct) {
        Product product = productMapper.toEntity(newProduct);
        productRepository.save(product);
    }

    public List<ProductView> getAllProducts() {
        return productMapper.toViews(productRepository.findAll());
    }

    public void deleteProductById(Long id) {
        Objects.requireNonNull(id);

        productRepository.deleteById(id);
    }

    public ProductView getProduct(Long productId) {
        return productMapper.toView(productRepository.get(productId));
    }

    public void importProducts(InputStream inputStream) {
        List<ExcelProduct> products = productExcelReader.readFile(inputStream);
        List<Product> toSave = productParser.parseProducts(products);
        productRepository.saveAll(toSave);
    }
}
