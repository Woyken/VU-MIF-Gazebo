package lt.vu.mif.ui.controller;

import java.math.BigDecimal;
import javax.transaction.Transactional;
import lt.vu.mif.model.product.Category;
import lt.vu.mif.model.product.Product;
import lt.vu.mif.repository.repository.interfaces.ICategoryRepository;
import lt.vu.mif.repository.repository.interfaces.IProductRepository;
import lt.vu.mif.ui.helpers.interfaces.IProductHelper;
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
    private IProductRepository productRepository;
    @Autowired
    private ICategoryRepository categoryRepository;
    @Autowired
    private IProductHelper productHelper;

    private ProductController productController;

    @Before
    public void init() {
        //Since @ViewScoped is JSF annotation, SpringBoot and JSF annotations can't be mixed. Creating this here, and setting autowired fields manually.
        productController = new ProductController();
        productController.setProductHelper(productHelper);

        Category category = new Category();
        category.setName("someCategory");
        categoryRepository.save(category);

        Product product = new Product();
        product.setTitle("Very long title");
        product.setDescription("Description 1");
        product.setPrice(new BigDecimal(10L));
        product.setSku("sku1");
        product.setCategory(category);
        productRepository.save(product);

        product = new Product();
        product.setTitle("long title");
        product.setDescription("Description 2");
        product.setPrice(new BigDecimal(20L));
        product.setSku("sku2");
        product.setCategory(category);
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
}
