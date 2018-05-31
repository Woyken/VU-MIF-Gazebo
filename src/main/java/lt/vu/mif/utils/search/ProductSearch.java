package lt.vu.mif.utils.search;

import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.model.product.Category;
import lt.vu.mif.model.product.ProductAttributeValue;

@Getter
@Setter
public class ProductSearch {
    private String title;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private List<Category> categories;
    private List<ProductAttributeValue> attributeValues;
    private boolean includeDeleted = false;
    private ProductSortEnum sortBy = ProductSortEnum.CREATE_DATE;

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