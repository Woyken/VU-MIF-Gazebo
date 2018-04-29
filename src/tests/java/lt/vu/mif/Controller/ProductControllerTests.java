package lt.vu.mif.Controller;

import java.math.BigDecimal;
import java.util.List;
import javax.transaction.Transactional;
import lt.vu.mif.Entity.Product;
import lt.vu.mif.Repository.ProductRepository;
import lt.vu.mif.Search.ProductSearch;
import lt.vu.mif.Service.UserService;
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

    private ProductController productController;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserService userService;

    @Before
    public void init() {
        //Since @ViewScoped is JSF annotation, SpringBoot and JSF annotations can't be mixed. Creating this here, and setting autowired fields manually.
        productController = new ProductController();
        productController.setProductRepository(productRepository);
        productController.setUserService(userService);

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
        productController.getProductSearch().setTitle("Very");
        productController.searchProducts();
        Assertions.assertEquals(1, productController.getProductsPage().getContent().size());
        Assertions.assertEquals("Very long title", productController.getProductsPage().getContent().get(0).getTitle());
    }

    @Test
    public void searchProducts_validSearch_multipleResults() {
        productController.getProductSearch().setTitle("long");
        productController.searchProducts();
        Assertions.assertEquals(2, productController.getProductsPage().getContent().size());
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
