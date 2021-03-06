package lt.vu.mif.ui.view;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;

@Named
@Getter
@Setter
public class ProductView {

    private Long id;
    private String sku;
    private String title;
    private BigDecimal newPrice;
    private BigDecimal price;
    private String description;
    private DiscountView discount;
    private CategoryView category;
    private List<ImageView> images = new ArrayList<>();
    private Integer version;

    public ProductView() {
    }

    public ProductView(ProductView other) {
        this.id = other.id;
        this.sku = other.sku;
        this.title = other.title;
        this.price = other.price;
        this.newPrice = other.newPrice;
        this.description = other.description;
        this.discount = other.getDiscount() == null ? null : new DiscountView(other.getDiscount());
        this.category = other.getCategory() == null ? null : new CategoryView(other.getCategory());
        this.images = new ArrayList<>(other.images);
    }
}