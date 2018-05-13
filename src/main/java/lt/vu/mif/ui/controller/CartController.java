package lt.vu.mif.ui.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.ui.helpers.implementations.ProductHelper;
import lt.vu.mif.ui.view.CartProductView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.SessionScope;

@Named
@Getter
@Setter
@SessionScope
public class CartController implements Serializable {

    @Autowired
    private ProductHelper productHelper;

    private List<CartProductView> productsInCart = new ArrayList<>();
    private CartProductView productSelectedForRemoval;
    private Boolean redirectAfterLogin = false;

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
        CartProductView productToAdd = productHelper.getCartProductView(id);
        productToAdd.setAmount(amount);
        productsInCart.add(productToAdd);
    }

    public String buyProducts() {
        return "payment?faces-redirect=true";
    }

    public String buyWithoutLogin() {
        redirectAfterLogin = true;
        return "login?faces-redirect=true";
    }
}
