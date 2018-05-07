package lt.vu.mif.repository.repository.interfaces;

import java.util.List;
import lt.vu.mif.model.order.Order;

public interface IOrderRepository {

    List<Order> getAllUserOrders(Long userId);

    void saveOrder(Order order);

    List<Order> findAll();

    <S extends Order> List<S> saveAll(Iterable<S> entities);
}
