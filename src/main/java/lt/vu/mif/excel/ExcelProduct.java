package lt.vu.mif.excel;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.model.product.Category;

@Getter
@Setter
public class ExcelProduct {

    private String skuCode;
    private String title;
    private String name;
    private String description;
    private BigDecimal price;
    private byte[] imageBytes;
    private Category category;
}