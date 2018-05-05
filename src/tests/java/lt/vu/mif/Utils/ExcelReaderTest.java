package lt.vu.mif.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

@RunWith(SpringRunner.class)
public class ExcelReaderTest {
    private static final String FILE_NAME = "/products.xlsx";

    @Test
    public void testExcelRead() throws Exception {
        File fileReadFrom = ResourceUtils.getFile(this.getClass().getResource(FILE_NAME));
        List<ExcelProduct> productList = ProductExcelReader.readFile(new FileInputStream(fileReadFrom));

        ProductParser.parseProducts(productList);
    }
}