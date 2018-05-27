package lt.vu.mif.Excel;

import java.io.File;
import java.io.FileInputStream;
import lt.vu.mif.excel.ImportResult;
import lt.vu.mif.excel.ProductExcelReader;
import lt.vu.mif.model.product.Category;
import lt.vu.mif.repository.repository.interfaces.ICategoryRepository;
import lt.vu.mif.utils.implementations.ProductParser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ExcelReaderTest {

    private static final String FILE_NAME = "/products.xlsx";

    @Autowired
    private ProductExcelReader productExcelReader;
    @Autowired
    private ProductParser productParser;
    @Autowired
    private ICategoryRepository categoryRepository;

    @Before
    public void init() {
        Category electronics = new Category();
        electronics.setName("electronics");

        Category phones = new Category();
        phones.setName("phones");

        Category smartPhones = new Category();
        smartPhones.setName("smart-phones");

        smartPhones.setParentCategory(phones);
        phones.setParentCategory(electronics);

        Category other = new Category();
        other.setName("other");

        Category smartWatches = new Category();
        smartWatches.setName("smart-watches");

        smartWatches.setParentCategory(other);
        other.setParentCategory(electronics);

        Category houseAppliances = new Category();
        houseAppliances.setName("house-appliances");

        Category other2 = new Category();
        other2.setName("other2");

        other2.setParentCategory(houseAppliances);
        houseAppliances.setParentCategory(electronics);

        Category test = new Category();
        test.setName("TEST");

        categoryRepository.save(electronics);
        categoryRepository.save(phones);
        categoryRepository.save(smartPhones);
        categoryRepository.save(other);
        categoryRepository.save(smartWatches);
        categoryRepository.save(houseAppliances);
        categoryRepository.save(other2);
        categoryRepository.save(test);
    }

    @Test
    public void testExcelRead() throws Exception {
        File fileReadFrom = ResourceUtils.getFile(this.getClass().getResource(FILE_NAME));
        ImportResult productList = productExcelReader
            .readFileSync(new FileInputStream(fileReadFrom));

        if (productList.getMessage() != null) {
            throw new IllegalStateException(productList.getMessage());
        }

        productParser.parseProducts(productList);

        if (productList.getMessage() != null) {
            throw new IllegalStateException(productList.getMessage());
        }
    }
}