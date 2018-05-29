package lt.vu.mif.utils.search;

import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.model.product.Category;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ProductSearch {
    private String title;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private List<Category> categories;
    private boolean includeDeleted = false;
    private ProductSortEnum sortBy = ProductSortEnum.CREATE_DATE;

    public void reset() {
        title = null;
        minPrice = null;
        maxPrice = null;
        categories = null;
        includeDeleted = false;
        sortBy = ProductSortEnum.CREATE_DATE;
    }
}