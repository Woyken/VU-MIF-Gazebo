package lt.vu.mif.Controller;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import lt.vu.mif.Entity.Product;
import lt.vu.mif.Repository.ProductRepository;
import lt.vu.mif.View.ProductView;

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
        productRepository.save(product);

        product = new Product();
        product.setTitle("long title");
        product.setDescription("Description 2");
        product.setPrice(new BigDecimal(20L));

        productRepository.save(product);
    }

    @Test
    public void searchProducts_emptySearch_fail() {
        Assertions.assertThrows(IllegalArgumentException.class, ()->{productController.searchProducts("");});
    }

    @Test
    public void searchProducts_validSearch_singleResult() {
        List<ProductView> result =  productController.searchProducts("Very");
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("Very long title", result.get(0).getTitle());
    }

    @Test
    public void searchProducts_validSearch_multipleResults() {
        List<ProductView> result =  productController.searchProducts("long");
        Assertions.assertEquals(2, result.size());
    }
}
