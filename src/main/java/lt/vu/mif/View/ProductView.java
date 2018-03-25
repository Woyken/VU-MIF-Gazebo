package lt.vu.mif.View;

import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.Entity.Product;

import javax.inject.Named;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Named
@Getter
@Setter
public class ProductView {
    private Long id;
    private String title;
    private BigDecimal price;
    private String description;
    private List<ImageView> images;

    public ProductView() {
    }

    public ProductView(Product product) {
        this.id = product.getId();
        this.title = product.getTitle();
        this.price = product.getPrice();
        this.description = product.getDescription();
        this.images = product.getImages().stream().map(image -> new ImageView(image)).collect(Collectors.toList());
    }

    public ProductView(Long id, String title, BigDecimal price, String description, List<ImageView> images) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.description = description;
        this.images = new ArrayList<>(images);
    }
}