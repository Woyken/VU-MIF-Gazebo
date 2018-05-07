package lt.vu.mif.bl.interfaces;

import java.util.List;
import lt.vu.mif.excel.ExcelProduct;
import lt.vu.mif.model.product.Product;

public interface IProductParser {
    List<Product> parseProducts(List<ExcelProduct> excelProducts);
}
