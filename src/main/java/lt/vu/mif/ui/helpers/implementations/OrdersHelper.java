package lt.vu.mif.ui.helpers.implementations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lt.vu.mif.authentication.UserService;
import lt.vu.mif.model.order.Order;
import lt.vu.mif.model.order.OrderProduct;
import lt.vu.mif.model.order.OrderStatus;
import lt.vu.mif.model.product.Product;
import lt.vu.mif.repository.repository.interfaces.IOrderRepository;
import lt.vu.mif.repository.repository.interfaces.IProductRepository;
import lt.vu.mif.ui.helpers.interfaces.IOrdersHelper;
import lt.vu.mif.ui.mappers.implementations.OrderMapper;
import lt.vu.mif.ui.view.CartProductView;
import lt.vu.mif.ui.view.OrderView;
import lt.vu.mif.ui.view.ProductView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrdersHelper implements IOrdersHelper {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private IOrderRepository orderRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private IProductRepository productRepository;

    @Override
    public List<OrderView> getAllOrders() {
        return orderMapper.toViews(orderRepository.findAll());
    }

    @Override
    public List<OrderView> getAllUserOrders(String email) {
        return orderMapper.toViews(orderRepository.getAllUserOrders(email));
    }

    @Override
    public OrderView getOrder(Long orderId) {
        return orderMapper.toView(orderRepository.get(orderId));
    }

    @Override
    public void saveNewOrder(OrderView orderView, List<CartProductView> cartProductViews) {
        Order order = new Order();
        order.setCreationDate(LocalDateTime.now());
        order.setStatus(OrderStatus.ACCEPTED);
        order.setUser(userService.getLoggedUser());
        order.setProducts(getOrderProducts(order, cartProductViews));
        order.setRating(orderView.getRating());
        orderRepository.save(order);
    }

    private List<OrderProduct> getOrderProducts(Order order, List<CartProductView> productViews) {
        List<Product> products = productRepository.get(
            productViews.stream().map(ProductView::getId).collect(Collectors
                .toList()));
        List<OrderProduct> orderProducts = new ArrayList<>();

        for (Product product : products) {
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setProduct(product);
            orderProduct.setQuantity(productViews.stream()
                .filter(t -> t.getId().equals(product.getId())).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Cannot find product"))
                .getAmount());
            orderProducts.add(orderProduct);
            orderProduct.setOrder(order);
        }

        return orderProducts;
    }

}
