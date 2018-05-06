package lt.vu.mif.Controller;

import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.Repository.OrderRepository;
import lt.vu.mif.Repository.UserRepository;
import lt.vu.mif.View.OrderView;
import lt.vu.mif.View.UserView;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Named
public class OrdersController {
    @Autowired
    private OrderRepository orderRepository;

    private List<OrderView> orders = new ArrayList<>();

    public void onPageLoad() {
        orders = orderRepository.getAll().stream().map(OrderView::new).collect(Collectors.toList());
    }

}
