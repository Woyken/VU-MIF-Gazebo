package lt.vu.mif.ui.view;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.model.order.OrderStatus;

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
