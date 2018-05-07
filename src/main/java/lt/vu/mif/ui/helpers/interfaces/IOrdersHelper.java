package lt.vu.mif.ui.helpers.interfaces;

import lt.vu.mif.ui.view.CartProductView;
import lt.vu.mif.ui.view.OrderView;

import java.util.List;

public interface IOrdersHelper {
    List<OrderView> getAllOrders();

    void saveNewOrder(OrderView orderView, List<CartProductView> cartProductViews);
}
