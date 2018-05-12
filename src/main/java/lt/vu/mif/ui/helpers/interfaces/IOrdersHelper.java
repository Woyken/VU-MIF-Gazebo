package lt.vu.mif.ui.helpers.interfaces;

import java.util.List;
import lt.vu.mif.ui.view.CartProductView;
import lt.vu.mif.ui.view.OrderView;

public interface IOrdersHelper {

    List<OrderView> getAllOrders();

    void saveNewOrder(OrderView orderView, List<CartProductView> cartProductViews);

    OrderView getOrder(Long orderId);
}
