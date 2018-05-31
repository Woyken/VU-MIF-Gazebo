package lt.vu.mif.excel;

import java.util.ArrayList;
import java.util.List;

public class ImportResult {

    private List<ExcelProduct> products = new ArrayList<>();
    private String message;

    public List<ExcelProduct> getProducts() {
        return products;
    }

    public void setProducts(List<ExcelProduct> products) {
        this.products = products;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}