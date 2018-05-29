package lt.vu.mif.ui.helpers.implementations;

import lt.vu.mif.authentication.UserService;
import lt.vu.mif.model.order.Order;
import lt.vu.mif.model.order.Rating;
import lt.vu.mif.repository.repository.interfaces.IOrderRepository;
import lt.vu.mif.repository.repository.interfaces.IRatingRepository;
import lt.vu.mif.ui.helpers.interfaces.IRatingHelper;
import lt.vu.mif.ui.mappers.implementations.OrderMapper;
import lt.vu.mif.ui.mappers.implementations.RatingMapper;
import lt.vu.mif.ui.view.OrderView;
import lt.vu.mif.ui.view.RatingView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Component
public class RatingHelper implements IRatingHelper {

    @Autowired
    private IOrderRepository orderRepository;
    @Autowired
    private IRatingRepository ratingRepository;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private RatingMapper ratingMapper;

    @Override
    public List<OrderView> getCurrentUserOrdersToRate() {
        return orderMapper
                .toViews(orderRepository.getCurrentUserOrdersToRate(userService.getLoggedUserEmail()));
    }

    @Override
    public void rateOrders(List<RatingView> ratingViews) {
        List<Rating> ratings = ratingMapper.toEntities(ratingViews);
        ratingRepository.saveAll(ratings);

        List<Order> orders = orderRepository.getOrders(
                ratingViews.stream().map(RatingView::getOrderId).collect(Collectors.toList()));

        orders.forEach(order -> order.setRated(true));
        orderRepository.updateAll(orders);
    }
}