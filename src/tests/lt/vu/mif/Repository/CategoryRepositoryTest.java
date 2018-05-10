package lt.vu.mif.Repository;

import java.math.BigDecimal;
import java.util.List;
import javax.transaction.Transactional;
import lt.vu.mif.model.product.Product;
import lt.vu.mif.repository.repository.implementations.Category;
import lt.vu.mif.repository.repository.interfaces.ICategoryRepository;
import lt.vu.mif.repository.repository.interfaces.IProductRepository;
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
public class CategoryRepositoryTest {

    @Autowired
    private ICategoryRepository categoryRepository;
    @Autowired
    private IProductRepository productRepository;

    @Before
    public void init() {
        createProducts();
        createCategories();
    }

    @Test
    public void assignCategoryToProductTest() {
        List<Product> products = productRepository.findAll();
        List<Category> categories = categoryRepository.findAll();

        for (Product product : products) {
            product.getCategories().addAll(categories);
        }

        productRepository.updateAll(products);

        List<Product> updatedProducts = productRepository.findAll();

        for (Product product : updatedProducts) {
            Assertions.assertFalse(product.getCategories().isEmpty());
        }
    }

    @Test
    public void createProductWithCategory() {
        Product product = new Product();
        product.setTitle("Very long title");
        product.setDescription("Description 1");
        product.setPrice(new BigDecimal(10L));
        product.setSku("sku1");
        productRepository.save(product);
        product.getCategories().add(getDefaultCategory());

        productRepository.save(product);

        Product createdProduct = productRepository.get(product.getId());
        Assertions.assertFalse(createdProduct.getCategories().isEmpty());
    }

    private void createCategories() {
        categoryRepository.save(getDefaultCategory());
    }

    private Category getDefaultCategory() {
        Category category = new Category();
        category.setName("furniture");

        category.getAttributes().add("color");
        category.getAttributes().add("size");

        Category subcategory = new Category();
        subcategory.setName("kitchen");

        category.setSubcategory(subcategory);

        return category;
    }

    private void createProducts() {
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
}
