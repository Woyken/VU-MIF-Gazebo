package lt.vu.mif.utils.search;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.model.product.Category;

@Getter
@Setter
public class ProductSearch {
    private String title;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private List<Category> categories;
    private List<Long> selectedAttributeValues = new ArrayList<>();
    private boolean includeDeleted = false;
    private ProductSortEnum sortBy = ProductSortEnum.CREATE_DATE;

    public void reset() {
        title = null;
        minPrice = null;
        maxPrice = null;
        categories = null;
        selectedAttributeValues = new ArrayList<>();
        includeDeleted = false;
        sortBy = ProductSortEnum.CREATE_DATE;
    }
}