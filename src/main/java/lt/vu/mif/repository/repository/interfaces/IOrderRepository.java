package lt.vu.mif.repository.repository.interfaces;

import java.util.List;
import lt.vu.mif.model.order.Order;

public interface IOrderRepository {

    List<Order> getAllUserOrders(Long userId);

    Order save(Order order);

    List<Order> findAll();

    <S extends Order> List<S> saveAll(Iterable<S> entities);

    Order get(Long Id);
}
