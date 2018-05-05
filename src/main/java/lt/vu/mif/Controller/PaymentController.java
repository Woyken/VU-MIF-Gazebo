package lt.vu.mif.Controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.Entity.Order;
import lt.vu.mif.Entity.OrderProduct;
import lt.vu.mif.Entity.OrderStatus;
import lt.vu.mif.Entity.Product;
import lt.vu.mif.Payments.PaymentResponse;
import lt.vu.mif.Payments.PaymentService;
import lt.vu.mif.Repository.OrderRepository;
import lt.vu.mif.Repository.ProductRepository;
import lt.vu.mif.Service.UserService;
import lt.vu.mif.View.ProductView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.RequestScope;

@Named
@RequestScope
@Getter
@Setter
public class PaymentController {

    @Autowired
    private CartController cartController;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductRepository productRepository;

    private String message;
    private String name;
    private String surname;
    private String address;
    private String cardNumber;
    private String yearDesktop;
    private String monthDesktop;
    private String cvsDesktop;
    private String yearMobile;
    private String monthMobile;
    private String cvsMobile;

    public void onPageLoad() throws IOException {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            return;
        }

        //If someone tried to reach payment page without having items in cart
        if (cartController.getProductsInCart().isEmpty()) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("cart.xhtml");
        }

        String error = FacesContext.getCurrentInstance().getExternalContext()
            .getRequestParameterMap().get("error");

        //Could be null if user newly entered the page
        if (error != null) {
            switch (error) {
                case "OutOfFunds":
                    message = "Jūsų kortelėje nepakanka pinigų";
                    return;
                case "CardExpired":
                    message = "Jūsų kortelė nebegalioja";
                    return;
                case "ValidationError":
                    message = "Neteisingi kortelės duomenys";
                    return;
                default:
                    message = "Apmokėjimo servisas grąžino klaidą: \"" + error + "\"";
            }
        }

    }

    public String makePayment() {
        String year = yearDesktop.isEmpty() ? yearMobile : yearDesktop;
        String month = monthDesktop.isEmpty() ? monthMobile : monthDesktop;
        String cvs = cvsDesktop.isEmpty() ? cvsMobile : cvsDesktop;

        PaymentResponse response = paymentService
            .MakePayment(cartController.getSum().movePointRight(2).intValueExact(), cardNumber,
                name + " " + surname, Integer.parseInt(year) + 2000, Integer.parseInt(month), cvs);

        if (!response.isSuccess()) {
            return "payment?error=" + response.getError().error + "&faces-redirect=true";
        }

        saveOrder();
        cartController.getProductsInCart().clear();
        return "main?faces-redirect=true";
    }

    private void saveOrder() {
        Order order = new Order();
        order.setCreationDate(LocalDateTime.now());
        order.setStatus(OrderStatus.ACCEPTED);
        order.setUser(userService.getLoggedUser());
        order.setProducts(getOrderProducts(order));

        orderRepository.save(order);
    }

    private List<OrderProduct> getOrderProducts(Order order) {
        List<Product> products = productRepository.get(
            cartController.getProductsInCart().stream().map(ProductView::getId).collect(Collectors
                .toList()));
        List<OrderProduct> orderProducts = new ArrayList<>();

        for (Product product : products) {
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setProduct(product);
            orderProduct.setQuantity(cartController.getProductsInCart().stream()
                .filter(t -> t.getId().equals(product.getId())).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Cannot find product"))
                .getAmount());
            orderProducts.add(orderProduct);
            orderProduct.setOrder(order);
        }

        return orderProducts;
    }
}
