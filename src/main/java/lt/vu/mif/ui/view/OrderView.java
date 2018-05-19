package lt.vu.mif.ui.view;

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
    private OrderStatus status;
    private String creationDate;
    private List<ProductView> products = new ArrayList<>();
}