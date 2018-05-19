package lt.vu.mif.ui.helpers.interfaces;

import java.util.List;

import lt.vu.mif.model.order.OrderStatus;
import lt.vu.mif.ui.view.CartProductView;
import lt.vu.mif.ui.view.OrderView;

public interface IOrdersHelper {

    List<OrderView> getAllOrders();

    List<OrderView> getAllUserOrders(String email);

    void saveNewOrder(OrderView orderView, List<CartProductView> cartProductViews);

    OrderView getOrder(Long orderId);

    void setOrderStatus(OrderView orderView, OrderStatus status);
}
