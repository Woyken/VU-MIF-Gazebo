package lt.vu.mif.ui.controller;

import java.io.IOException;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.payment.PaymentResponse;
import lt.vu.mif.payment.PaymentService;
import lt.vu.mif.ui.helpers.interfaces.IOrdersHelper;
import lt.vu.mif.ui.view.OrderView;
import lt.vu.mif.utils.SessionManager;
import lt.vu.mif.utils.constants.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.SessionScope;

@Named
@Getter
@Setter
@SessionScope
public class PaymentController {

    @Autowired
    private CartController cartController;
    @Autowired
    private IOrdersHelper ordersHelper;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private SessionManager sessionManager;

    private OrderView orderView = new OrderView();

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
                    message = "Jūsų kortelėje nepakanka pinigų.";
                    return;
                case "CardExpired":
                    message = "Jūsų kortelė nebegalioja.";
                    return;
                case "ValidationError":
                    message = "Neteisingi kortelės duomenys.";
                    return;
                default:
                    message = "Atsiprašome, įvyko nenumatyta klaida.";
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

        ordersHelper.saveNewOrder(orderView, cartController.getProductsInCart());
        cartController.getProductsInCart().clear();

        sessionManager.setAttribute(Constants.SHOW_DIALOG_SESSION_PARAMETER, false);

        return "main?faces-redirect=true";
    }
}
