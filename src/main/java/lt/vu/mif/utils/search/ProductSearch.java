package lt.vu.mif.utils.search;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSearch {
    private String title;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private boolean includeDeleted = false;

    public void reset() {
        title = null;
        minPrice = null;
        maxPrice = null;
        includeDeleted = false;
    }
}