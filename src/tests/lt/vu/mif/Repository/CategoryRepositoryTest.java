package lt.vu.mif.Repository;

import java.math.BigDecimal;
import java.util.List;
import javax.transaction.Transactional;
import lt.vu.mif.model.product.Product;
import lt.vu.mif.model.product.Category;
import lt.vu.mif.repository.repository.interfaces.IProductRepository;
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
    private IProductRepository productRepository;


    @Test
    public void insertProductsWithCategoryTest() {
        Product newProduct = new Product();
        newProduct.setTitle("Very long title");
        newProduct.setDescription("Description 1");
        newProduct.setPrice(new BigDecimal(10L));
        newProduct.setSku("sku1");
        newProduct.setCategory(getDefaultCategory());
        productRepository.save(newProduct);

        List<Product> createdProducts = productRepository.findAll();

        for (Product product : createdProducts) {
            Assertions
                .assertEquals(getDefaultCategory().getName(), product.getCategory().getName());
        }
    }

    private Category getDefaultCategory() {
        Category parentCategory = new Category();
        parentCategory.setName("furniture");

        parentCategory.getAttributes().add("color");
        parentCategory.getAttributes().add("size");

        Category subcategory = new Category();
        subcategory.setName("kitchen");

        subcategory.setParentCategory(parentCategory);

        return parentCategory;
    }

}
