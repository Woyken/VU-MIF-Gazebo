package lt.vu.mif.Controller;

import java.util.List;
import lt.vu.mif.Entity.Product;
import lt.vu.mif.Repository.ProductRepository;
import lt.vu.mif.View.ProductView;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductControllerTests {

    @Autowired
    private ProductController productController;
    @Autowired
    private ProductRepository productRepository;

    @Before
    public void init() {
        Assertions.assertEquals(true, productRepository.getAll().isEmpty());

        Product product = new Product();
        product.setTitle("Very long title");
        productRepository.save(product);
        product = new Product();
        product.setTitle("long title");
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
