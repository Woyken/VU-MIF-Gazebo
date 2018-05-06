package lt.vu.mif.View;

import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.Entity.Order;
import lt.vu.mif.Entity.OrderProduct;
import lt.vu.mif.Entity.OrderStatus;
import lt.vu.mif.Entity.User;

import javax.inject.Named;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Named
@Getter
@Setter
public class OrderView {

    private Long id;
    private User user;
    private Long rating;
    private OrderStatus status;
    private LocalDateTime creationDate;
    private List<OrderProduct> products = new ArrayList<>();

    public OrderView() {
    }

    public OrderView(Order order) {
        this.id = order.getId();
        this.user = order.getUser();
        this.rating = order.getRating();
        this.status = order.getStatus();
        this.creationDate = order.getCreationDate();
        this.products = order.getProducts();
    }
}
