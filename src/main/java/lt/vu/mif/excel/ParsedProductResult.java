package lt.vu.mif.excel;

public class ParsedProductResult {

    private String message;
    private ExcelProduct excelProduct = new ExcelProduct();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ExcelProduct getExcelProduct() {
        return excelProduct;
    }

    public void setExcelProduct(ExcelProduct excelProduct) {
        this.excelProduct = excelProduct;
    }
}