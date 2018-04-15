package lt.vu.mif.Controller;

import java.math.BigDecimal;
import java.util.List;
import javax.transaction.Transactional;
import lt.vu.mif.Entity.Product;
import lt.vu.mif.Repository.ProductRepository;
import lt.vu.mif.Search.ProductSearch;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

@Rollback
@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductControllerTests {

    @Autowired
    private ProductController productController;
    @Autowired
    private ProductRepository productRepository;

    @Before
    public void init() {
        Assertions.assertTrue(productRepository.getAll().isEmpty());

        Product product = new Product();
        product.setTitle("Very long title");
        product.setDescription("Description 1");
        product.setPrice(new BigDecimal(10L));
        product.setSku("sku1");
        productRepository.save(product);

        product = new Product();
        product.setTitle("long title");
        product.setDescription("Description 2");
        product.setPrice(new BigDecimal(20L));
        product.setSku("sku2");
        productRepository.save(product);
    }

    @Test
    public void searchProducts_validSearch_singleResult() {
        productController.setSearchPhrase("Very");
        productController.searchProducts();
        Assertions.assertEquals(1, productController.getProducts().size());
        Assertions
            .assertEquals("Very long title", productController.getProducts().get(0).getTitle());
    }

    @Test
    public void searchProducts_validSearch_multipleResults() {
        productController.setSearchPhrase("long");
        productController.searchProducts();
        Assertions.assertEquals(2, productController.getProducts().size());
    }

    @Test
    public void searchProducts() {
        ProductSearch productSearch = new ProductSearch();

        List<Product> products = productRepository.findProducts(productSearch);
        Assertions.assertEquals(2, products.size());

        productSearch.setMinPrice(new BigDecimal(10L));
        products = productRepository.findProducts(productSearch);
        Assertions.assertEquals(2, products.size());

        productSearch.setMinPrice(new BigDecimal(12L));
        products = productRepository.findProducts(productSearch);
        Assertions.assertEquals(1, products.size());

        productSearch.setMaxPrice(new BigDecimal(20L));
        products = productRepository.findProducts(productSearch);
        Assertions.assertEquals(1, products.size());

        productSearch.setMaxPrice(new BigDecimal(50L));
        products = productRepository.findProducts(productSearch);
        Assertions.assertEquals(1, products.size());

        productSearch.setMaxPrice(new BigDecimal(15L));
        products = productRepository.findProducts(productSearch);
        Assertions.assertEquals(0, products.size());

        productSearch.setMinPrice(null);
        productSearch.setMaxPrice(new BigDecimal(20L));
        products = productRepository.findProducts(productSearch);
        Assertions.assertEquals(2, products.size());
    }
}
