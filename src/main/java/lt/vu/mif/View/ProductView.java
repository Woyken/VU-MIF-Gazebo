package lt.vu.mif.View;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.Entity.Product;

@Named
@Getter
@Setter
public class ProductView {
    private Long id;
    private String sku;
    private String title;
    private Long discount;
    private BigDecimal price;
    private String description;
    private List<ImageView> images = new ArrayList<>();

    public ProductView() {
    }

    public ProductView(ProductView other) {
        this.id = other.id;
        this.sku = other.sku;
        this.title = other.title;
        this.price = other.price;
        this.discount = other.discount;
        this.description = other.description;
        this.images = new ArrayList<>(other.images);
    }

    public ProductView(Product product) {
        this.id = product.getId();
        this.sku = product.getSku();
        this.title = product.getTitle();
        this.price = product.getPrice();
        this.discount = product.getDiscount();
        this.description = product.getDescription();
        this.images = product.getImages().stream().map(ImageView::new).collect(Collectors.toList());
    }

    public ProductView(Long id, String sku, String title, BigDecimal price, Long discount, String description,
        List<ImageView> images) {
        this.id = id;
        this.sku = sku;
        this.title = title;
        this.price = price;
        this.discount = discount;
        this.description = description;
        this.images = new ArrayList<>(images);
    }
}