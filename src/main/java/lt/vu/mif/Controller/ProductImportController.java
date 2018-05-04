package lt.vu.mif.Controller;

import java.io.IOException;
import java.util.List;
import javax.inject.Named;
import javax.servlet.http.Part;
import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.Entity.Product;
import lt.vu.mif.Repository.ProductRepository;
import lt.vu.mif.Utils.ExcelProduct;
import lt.vu.mif.Utils.ProductExcelReader;
import lt.vu.mif.Utils.ProductParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.RequestScope;

@Named
@Getter
@Setter
@RequestScope
public class ProductImportController {
    @Autowired
    private ProductRepository productRepository;

    private Part uploadedFile;
    private String message;

    public void importProducts() {
        try {
            if (uploadedFile != null) {
                List<ExcelProduct> products =  ProductExcelReader.readFile(uploadedFile.getInputStream());
                List<Product> toSave = ProductParser.parseProducts(products);
                productRepository.saveAll(toSave);
                message = "Importas sėkmingai atliktas";
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            message = "Įvyko klaida bandant importuoti failą";
        }
    }
}