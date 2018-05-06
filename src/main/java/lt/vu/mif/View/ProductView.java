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
    private BigDecimal newPrice;
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
        this.newPrice = other.newPrice;
        this.description = other.description;
        this.images = new ArrayList<>(other.images);
    }

    public ProductView(Product product) {
        this.id = product.getId();
        this.sku = product.getSku();
        this.title = product.getTitle();
        this.price = product.getPrice();
        this.newPrice = product.getNewPrice();
        this.description = product.getDescription();
        this.images = product.getImages().stream().map(ImageView::new).collect(Collectors.toList());
    }

    public ProductView(Long id, String sku, String title, BigDecimal price, BigDecimal newPrice, String description,
        List<ImageView> images) {
        this.id = id;
        this.sku = sku;
        this.title = title;
        this.price = price;
        this.newPrice = newPrice;
        this.description = description;
        this.images = new ArrayList<>(images);
    }
}