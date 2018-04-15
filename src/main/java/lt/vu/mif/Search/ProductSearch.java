package lt.vu.mif.Search;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSearch {
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
}
