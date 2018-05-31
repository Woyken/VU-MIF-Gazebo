package lt.vu.mif.Excel;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import lt.vu.mif.excel.ImportResult;
import lt.vu.mif.excel.ProductExcelReader;
import lt.vu.mif.model.product.Category;
import lt.vu.mif.model.product.CategoryAttribute;
import lt.vu.mif.model.product.CategoryAttributeValue;
import lt.vu.mif.model.product.Product;
import lt.vu.mif.repository.repository.interfaces.ICategoryRepository;
import lt.vu.mif.repository.repository.interfaces.IProductRepository;
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

    private static final String FILE_NAME = "/tests_products.xlsx";

    @Autowired
    private ProductExcelReader productExcelReader;
    @Autowired
    private ProductParser productParser;
    @Autowired
    private ICategoryRepository categoryRepository;
    @Autowired
    private IProductRepository productRepository;

    @Before
    public void init() {
        Category electronics = new Category();
        electronics.setName("electronics");

        CategoryAttribute color = new CategoryAttribute();
        color.setCategory(electronics);
        color.setName("color");

        CategoryAttributeValue black = new CategoryAttributeValue();
        black.setCategoryAttribute(color);
        black.setValue("black");

        CategoryAttributeValue red = new CategoryAttributeValue();
        red.setCategoryAttribute(color);
        red.setValue("red");

        CategoryAttributeValue white = new CategoryAttributeValue();
        white.setCategoryAttribute(color);
        white.setValue("white");

        color.getValues().add(black);
        color.getValues().add(white);

        electronics.getAttributes().add(color);

        Category phones = new Category();
        phones.setName("phones");
//
//        CategoryAttribute size = new CategoryAttribute();
//        size.setCategory(phones);
//        color.setName("size");
//
//        CategoryAttributeValue fifteen = new CategoryAttributeValue();
//        fifteen.setCategoryAttribute(size);
//        fifteen.setValue("15");
//
        Category smartPhones = new Category();
        smartPhones.setName("smart-phones");
//
//        CategoryAttribute manufacturerer = new CategoryAttribute();
//        manufacturerer.setCategory(smartPhones);
//        manufacturerer.setName("manufacturer");
//
//        CategoryAttribute bateryLife = new CategoryAttribute();
//        bateryLife.setCategory(smartPhones);
//        bateryLife.setName("batery life");
//
//        CategoryAttribute fifteen1 = new CategoryAttribute();
//        fifteen1.setCategory(smartPhones);
//        fifteen1.setName("15hrs");
//
//        CategoryAttributeValue something = new CategoryAttributeValue();
//        something.setCategoryAttribute(manufacturerer);
//        something.setValue("something");
//
//
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

        List<Product> products = productParser.parseProducts(productList);
        productRepository.saveAll(products);

        if (productList.getMessage() != null) {
            throw new IllegalStateException(productList.getMessage());
        }
    }
}