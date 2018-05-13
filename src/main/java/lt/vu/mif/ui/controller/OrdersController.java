package lt.vu.mif.ui.controller;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.ui.helpers.interfaces.IOrdersHelper;
import lt.vu.mif.ui.view.OrderView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.RequestScope;

@Getter
@Setter
@Named
@RequestScope
public class OrdersController {

    @Autowired
    private IOrdersHelper ordersHelper;

    private OrderView orderView;

    private List<OrderView> orders = new ArrayList<>();

    private List<OrderView> userOrders = new ArrayList<>();

    public void onPageLoad() {
        orders = ordersHelper.getAllOrders();
    }

    public void onPageLoadUser(String email) {
        userOrders = ordersHelper.getAllUserOrders(email);
    }

    public List<OrderView> getOrdersByUser(String email){
        return ordersHelper.getAllUserOrders(email);
    }

}
