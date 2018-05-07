package lt.vu.mif.ui.controller;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.ui.helpers.interfaces.IOrdersHelper;
import lt.vu.mif.ui.view.OrderView;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
@Named
public class OrdersController {

    @Autowired
    private IOrdersHelper ordersHelper;

    private List<OrderView> orders = new ArrayList<>();

    public void onPageLoad() {
        orders = ordersHelper.getAllOrders();
    }

}
