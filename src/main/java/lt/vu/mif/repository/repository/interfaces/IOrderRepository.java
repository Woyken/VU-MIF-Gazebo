package lt.vu.mif.repository.repository.interfaces;

import java.util.List;
import lt.vu.mif.model.order.Order;

public interface IOrderRepository {

    List<Order> getAllUserOrders(String email);

    Order save(Order order);

    List<Order> findAll();

    <S extends Order> List<S> saveAll(Iterable<S> entities);

    List<Order> getCurrentUserOrdersToRate(String currentUserEmail);

    Order get(Long id);

    List<Order> getOrders(List<Long> ids);

    void updateAll(List<Order> orders);

    void update(Order order);
}
