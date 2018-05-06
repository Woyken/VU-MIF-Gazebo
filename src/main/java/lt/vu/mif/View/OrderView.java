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
    private Long rating;

    public OrderView() {
    }

    public OrderView(Order order) {
        this.rating = order.getRating();
    }
}
