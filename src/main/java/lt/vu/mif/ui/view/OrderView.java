package lt.vu.mif.ui.view;

import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.model.order.OrderStatus;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

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