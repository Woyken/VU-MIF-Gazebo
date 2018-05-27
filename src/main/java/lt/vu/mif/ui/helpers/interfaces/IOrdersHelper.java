package lt.vu.mif.ui.helpers.interfaces;

import java.util.List;
import lt.vu.mif.model.order.OrderStatus;
import lt.vu.mif.ui.view.AdminOrderPreview;
import lt.vu.mif.ui.view.CartItemView;
import lt.vu.mif.ui.view.OrderPreview;
import lt.vu.mif.ui.view.OrderView;

public interface IOrdersHelper {

    List<OrderView> getAllOrders();

    List<OrderPreview> getAllUserOrders(String email);

    void saveNewOrder(OrderView orderView, List<CartItemView> cartItemViews);

    OrderView getOrder(Long orderId);

    void setOrderStatus(Long orderId, OrderStatus status);

    AdminOrderPreview getAdminOrder(Long orderId);

    List<OrderPreview> getAllAdminOrders();
}
