package lt.vu.mif.ui.view;

import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.utils.search.ProductSortEnum;

@Getter
@Setter
public class ProductSearchView {

    private String title;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private List<CategoryView> categories;
    private List<AttributeValue> attributeValues;
    private boolean includeDeleted = false;
    private ProductSortEnum sortBy;

    public void reset() {
        title = null;
        minPrice = null;
        maxPrice = null;
        categories = null;
        attributeValues = null;
        includeDeleted = false;
        sortBy = ProductSortEnum.CREATE_DATE;
    }
}