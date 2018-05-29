package lt.vu.mif.Excel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lt.vu.mif.excel.ProductExcelWriter;
import lt.vu.mif.model.product.Category;
import lt.vu.mif.model.product.Product;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ExcelWriterTest {
    @Autowired
    private ProductExcelWriter productExcelWriter;

    @Test
    public void writeTest() {
        Workbook workbook = productExcelWriter.createWorkBook(getMockProducts());
        productExcelWriter.write(workbook);
    }

    private List<Product> getMockProducts() {
        List<Product> products = new ArrayList<>();


        Category category = new Category();
        category.setName("someCategory");

        Product product = new Product();
        product.setTitle("Very long title");
        product.setDescription("Description 1");
        product.setPrice(new BigDecimal(10L));
        product.setSku("sku1");
        product.setCategory(category);

        Product product1 = new Product();
        product1.setTitle("Very long title");
        product1.setDescription("Description 1");
        product1.setPrice(new BigDecimal(10L));
        product1.setSku("sku1");
        product1.setCategory(category);

        products.add(product);
        products.add(product1);


        return products;
    }
}