package lt.vu.mif.Excel;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import lt.vu.mif.excel.ImportResult;
import lt.vu.mif.excel.ProductExcelReader;
import lt.vu.mif.generator.interfaces.IDataGenerator;
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

    private static final String FILE_NAME = "/products.xlsx";

    @Autowired
    private ProductExcelReader productExcelReader;
    @Autowired
    private ProductParser productParser;
    @Autowired
    private ICategoryRepository categoryRepository;
    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private IDataGenerator dataGenerator;

    @Before
    public void init() {
        dataGenerator.generateData();
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