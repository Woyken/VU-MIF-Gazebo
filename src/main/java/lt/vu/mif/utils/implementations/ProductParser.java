package lt.vu.mif.utils.implementations;

import java.util.ArrayList;
import java.util.List;
import lt.vu.mif.excel.ExcelProduct;
import lt.vu.mif.excel.ImportResult;
import lt.vu.mif.model.product.Image;
import lt.vu.mif.model.product.Product;
import lt.vu.mif.utils.interfaces.IProductParser;
import org.springframework.stereotype.Component;

@Component
public class ProductParser implements IProductParser {

    public List<Product> parseProducts(ImportResult importResult) {
        List<Product> result = new ArrayList<>();

        for (ExcelProduct excelProduct : importResult.getProducts()) {
            Product product = new Product();
            product.setSku(excelProduct.getSkuCode());
            product.setTitle(excelProduct.getTitle());
            product.setDescription(excelProduct.getDescription());
            product.setPrice(excelProduct.getPrice());
            product.getImages().add(new Image(excelProduct.getImageBytes()));
            result.add(product);
        }
        return result;
    }
}