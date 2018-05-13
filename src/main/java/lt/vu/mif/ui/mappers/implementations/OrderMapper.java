package lt.vu.mif.ui.mappers.implementations;

import java.util.List;
import java.util.stream.Collectors;
import lt.vu.mif.model.order.Order;
import lt.vu.mif.model.order.OrderProduct;
import lt.vu.mif.ui.mappers.interfaces.IMapper;
import lt.vu.mif.ui.view.OrderView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("orderMapper")
public class OrderMapper implements IMapper<Order, OrderView> {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public Order toEntity(OrderView view) {
        return null;
    }

    @Override
    public List<Order> toEntities(List<OrderView> views) {
        return null;
    }

    @Override
    public OrderView toView(Order entity) {
        OrderView view = new OrderView();

        view.setCreationDate(entity.getCreationDate());
        view.setId(entity.getId());
        view.setRating(entity.getRating());
        view.setStatus(entity.getStatus());
        view.setUser(userMapper.toView(entity.getUser()));
        view.setProducts(productMapper.toViews(entity.getProducts().stream().map(OrderProduct::getProduct).collect(Collectors.toList())));

        return view;
    }

    @Override
    public List<OrderView> toViews(List<Order> entities) {
        return entities.stream().map(this::toView).collect(Collectors.toList());
    }
}
