package lt.vu.mif.ui.mappers.implementations;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import lt.vu.mif.model.order.Order;
import lt.vu.mif.model.order.OrderProduct;
import lt.vu.mif.model.user.User;
import lt.vu.mif.ui.mappers.interfaces.IMapper;
import lt.vu.mif.ui.view.AdminOrderPreview;
import lt.vu.mif.ui.view.OrderPreview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Component
public class OrderPreviewMapper implements IMapper<Order, OrderPreview> {

    @Autowired
    private RatingMapper ratingMapper;
    @Autowired
    private BoughtProductMapper boughtProductMapper;

    @Override
    public Order toEntity(OrderPreview view) {
        throw new NotImplementedException();
    }

    @Override
    public List<Order> toEntities(List<OrderPreview> views) {
        throw new NotImplementedException();
    }

    @Override
    public OrderPreview toView(Order entity) {
        OrderPreview view = new OrderPreview();

        view.setCreationDate(entity.getCreationDate()
            .format(DateTimeFormatter.ofPattern("yyyy-mm-dd hh:mm")));
        view.setId(entity.getId());
        view.setStatus(entity.getStatus());
        User user = entity.getUser();
        view.setUserEmail(user.getEmail());
        view.setUserId(user.getId());
        view.setProducts(boughtProductMapper.toViews(entity.getProducts()));

        return view;
    }

    public AdminOrderPreview toAdminOrderPreview(Order entity) {
        AdminOrderPreview view = new AdminOrderPreview();

        view.setCreationDate(entity.getCreationDate()
            .format(DateTimeFormatter.ofPattern("yyyy-mm-dd hh:mm")));
        view.setId(entity.getId());
        view.setStatus(entity.getStatus());
        User user = entity.getUser();
        view.setUserEmail(user.getEmail());
        view.setUserId(user.getId());
        view.setProducts(boughtProductMapper.toViews(entity.getProducts()));
        view.setRatingView(ratingMapper.toView(entity.getRating()));
        view.setTotalSum(getOrderTotalPrice(entity));

        return view;

    }

    @Override
    public List<OrderPreview> toViews(List<Order> entities) {
        return entities.stream().map(this::toView).collect(Collectors.toList());
    }

    private BigDecimal getOrderTotalPrice(Order order) {
        List<OrderProduct> products = order.getProducts();

        if (CollectionUtils.isEmpty(products)) {
            return new BigDecimal(0L);
        }

        BigDecimal totalPrice = new BigDecimal(0L);

        for (OrderProduct orderProduct : products) {
            totalPrice = totalPrice
                .add(orderProduct.getPrice().multiply(new BigDecimal(orderProduct.getQuantity())));
        }

        return totalPrice;
    }
}