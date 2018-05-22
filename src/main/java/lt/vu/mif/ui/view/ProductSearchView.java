package lt.vu.mif.ui.view;

import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSearchView {

    private String title;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private List<CategoryView> categories;
    private boolean includeDeleted = false;

    public void reset() {
        title = null;
        minPrice = null;
        maxPrice = null;
        categories = null;
        includeDeleted = false;
    }
}