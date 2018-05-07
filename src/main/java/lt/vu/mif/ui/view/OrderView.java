package lt.vu.mif.ui.view;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.model.order.OrderStatus;

import javax.inject.Named;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Named
@Getter
@Setter
public class OrderView {

    private Long id;
    private UserView user;
    private Long rating;
    private OrderStatus status;
    private LocalDateTime creationDate;
    private List<ProductView> products = new ArrayList<>();
}
