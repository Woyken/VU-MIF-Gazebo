package lt.vu.mif.utils.interfaces;

import java.util.List;
import lt.vu.mif.excel.ImportResult;
import lt.vu.mif.model.product.Product;

public interface IProductParser {

    List<Product> parseProducts(ImportResult importResult);
}
