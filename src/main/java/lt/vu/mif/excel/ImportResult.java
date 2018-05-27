package lt.vu.mif.excel;

import java.util.ArrayList;
import java.util.List;

public class ImportResult {

    private List<ExcelProduct> products = new ArrayList<>();
    private String message;

    public void setProducts(List<ExcelProduct> products) {
        this.products = products;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ExcelProduct> getProducts() {
        return products;
    }

    public String getMessage() {
        return message;
    }
}