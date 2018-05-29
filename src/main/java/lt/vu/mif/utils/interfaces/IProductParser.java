package lt.vu.mif.utils.interfaces;

import lt.vu.mif.excel.ImportResult;
import lt.vu.mif.model.product.Product;

import java.util.List;

public interface IProductParser {

    List<Product> parseProducts(ImportResult importResult);
}
