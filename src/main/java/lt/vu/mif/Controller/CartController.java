package lt.vu.mif.Controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.Entity.Order;
import lt.vu.mif.Entity.OrderProduct;
import lt.vu.mif.Entity.OrderStatus;
import lt.vu.mif.Entity.Product;
import lt.vu.mif.Repository.OrderRepository;
import lt.vu.mif.Repository.ProductRepository;
import lt.vu.mif.Service.UserService;
import lt.vu.mif.View.CartProductView;
import lt.vu.mif.View.ProductView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.annotation.SessionScope;

@Named
@Getter
@Setter
@SessionScope
public class CartController implements Serializable {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderRepository orderRepository;

    private List<CartProductView> productsInCart = new ArrayList<>();
    private CartProductView productSelectedForRemoval;

    public void selectForRemoval(CartProductView product) {
        productSelectedForRemoval = product;
    }

    public void removeSelectedProduct() {
        if (null != productSelectedForRemoval) {
            productsInCart.remove(productSelectedForRemoval);
        }
    }

    public void addProductToCart(Long id) {
        addOrIncrementProduct(id, 1L);
    }

    public void addProductToCart(Long id, Long amount) {
        addOrIncrementProduct(id, amount);
    }

    public BigDecimal getSum() {
        BigDecimal totalSum = new BigDecimal(0);
        for (CartProductView product : productsInCart) {
            BigDecimal productPrice = product.getPrice()
                .multiply(new BigDecimal(product.getAmount()));
            totalSum = totalSum.add(productPrice);
        }
        return totalSum;
    }

    private void addOrIncrementProduct(Long id, Long amount) {
        for (CartProductView product : productsInCart) {
            if (product.getId().equals(id)) {
                product.setAmount(product.getAmount() + amount);
                return;
            }
        }
        List<Long> idsList = new ArrayList<>();
        idsList.add(id);
        CartProductView productToAdd = new CartProductView(
                new ProductView(productRepository.get(idsList).get(0)));
        productToAdd.setAmount(amount);
        productsInCart.add(productToAdd);
    }

    public void buyProducts() {
        if (CollectionUtils.isEmpty(productsInCart)) {
            return;
        }
        Order order = new Order();
        order.setCreationDate(LocalDateTime.now());
        order.setStatus(OrderStatus.ACCEPTED);
        order.setUser(userService.getLoggedUser());
        order.setProducts(getOrderProducts(order));

        orderRepository.save(order);
    }

    private List<OrderProduct> getOrderProducts(Order order) {
        List<Product> products = productRepository.get(productsInCart.stream().map(ProductView::getId).collect(Collectors.toList()));
        List<OrderProduct> orderProducts = new ArrayList<>();

        for (Product product : products) {
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setProduct(product);
            orderProduct.setQuantity(productsInCart.stream().filter(t -> t.getId().equals(product.getId())).findFirst().orElseThrow(() -> new IllegalArgumentException("Cannot find product")).getAmount());
            orderProducts.add(orderProduct);
            orderProduct.setOrder(order);
        }

        return orderProducts;
    }
}
