package lt.vu.mif.View;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.Entity.OrderProduct;

@Getter
@Setter
public class BoughtProductView {
    private ImageView image;
    private String date;
    private long quantity;
    private BigDecimal price;
    private String title;
    private Long productId;
    private Long imageId;

    public BoughtProductView(OrderProduct orderProduct) {
        this.image = new ImageView(orderProduct.getProduct().getImages().get(0));
        this.date = orderProduct.getOrder().getCreationDate().format(DateTimeFormatter.ofPattern("yyyy-mm-dd hh:mm"));
        this.quantity = orderProduct.getQuantity();
        this.price = orderProduct.getProduct().getPrice().multiply(new BigDecimal(orderProduct.getQuantity()));
        this.title = orderProduct.getProduct().getTitle();
        this.productId = orderProduct.getProduct().getId();
    }
}