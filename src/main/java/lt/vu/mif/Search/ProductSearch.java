package lt.vu.mif.Search;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.Constants;

@Getter
@Setter
public class ProductSearch {
    private String title;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;

    private int pageSize = Constants.PAGE_SIZE;
    private int activePage = Constants.ACTIVE_PAGE;

    public int getActivePage() {
        return activePage;
    }
}
