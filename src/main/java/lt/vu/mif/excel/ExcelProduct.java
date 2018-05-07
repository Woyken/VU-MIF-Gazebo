package lt.vu.mif.excel;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExcelProduct {
    private String skuCode;
    private String title;
    private String description;
    private BigDecimal price;
    private String imageLink;
}