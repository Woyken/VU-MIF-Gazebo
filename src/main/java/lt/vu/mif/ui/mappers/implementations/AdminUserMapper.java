package lt.vu.mif.ui.mappers.implementations;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lt.vu.mif.model.order.Order;
import lt.vu.mif.model.order.OrderProduct;
import lt.vu.mif.model.user.User;
import lt.vu.mif.ui.mappers.interfaces.IMapper;
import lt.vu.mif.ui.view.AdminUserView;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Component
public class AdminUserMapper implements IMapper<User, AdminUserView> {

    @Override
    public User toEntity(AdminUserView view) {
        throw new NotImplementedException();
    }

    @Override
    public List<User> toEntities(List<AdminUserView> views) {
        throw new NotImplementedException();
    }

    @Override
    public AdminUserView toView(User entity) {
        AdminUserView view = new AdminUserView();

        view.setId(entity.getId());
        view.setBlocked(entity.isBlocked());
        view.setEmail(entity.getEmail());
        view.setPassword(entity.getPassword());
        view.setRole(entity.getRole());

        //Orders info
        view.setOrdersCount(entity.getOrders().size());
        view.setOrdersSum(getUserOrdersSum(entity));
        view.setAveragePrice(getAverageSum(view.getOrdersCount(), view.getOrdersSum()));

        return view;
    }

    @Override
    public List<AdminUserView> toViews(List<User> entities) {
        if (CollectionUtils.isEmpty(entities)) {
            return Collections.emptyList();
        }
        return entities.stream().map(this::toView).collect(Collectors.toList());
    }

    private BigDecimal getAverageSum(long ordersCount, BigDecimal ordersSum) {
        return ordersCount == 0 ? BigDecimal.ZERO
            : ordersSum.divide(new BigDecimal(ordersCount), 2);
    }

    private BigDecimal getUserOrdersSum(User user) {
        List<Order> orders = user.getOrders();

        if (CollectionUtils.isEmpty(orders)) {
            return new BigDecimal(0L);
        }

        BigDecimal totalSum = new BigDecimal(0);

        for (Order order : orders) {
            for (OrderProduct orderProduct : order.getProducts()) {
                totalSum = totalSum.add(
                    orderProduct.getPrice().multiply(new BigDecimal(orderProduct.getQuantity())));
            }
        }

        return totalSum;
    }
}
