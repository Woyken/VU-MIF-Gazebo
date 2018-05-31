package lt.vu.mif.excel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParsedProductResult {

    private String message;
    private ExcelProduct excelProduct = new ExcelProduct();
}