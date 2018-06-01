package lt.vu.mif.excel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private List<byte[]> imagesBytes = new ArrayList<>();
    private Category category;
    private Map<String, String> attributes = new HashMap<>();
}