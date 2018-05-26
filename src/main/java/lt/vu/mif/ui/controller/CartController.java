package lt.vu.mif.ui.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.ui.helpers.interfaces.IProductHelper;
import lt.vu.mif.ui.view.CartItemView;
import lt.vu.mif.ui.view.CartView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.SessionScope;

@Named
@Getter
@Setter
@SessionScope
public class CartController implements Serializable {

    @Autowired
    private IProductHelper productHelper;

    private List<CartItemView> productsInCart = new ArrayList<>();
    private CartItemView productSelectedForRemoval;
    private Boolean redirectAfterLogin = false;

    public void selectForRemoval(CartItemView product) {
        productSelectedForRemoval = product;
    }

    public void removeSelectedProduct() {
        if (null != productSelectedForRemoval) {
            productsInCart.remove(productSelectedForRemoval);
            saveUserCart();
        }
    }

    public void addProductToCart(Long id) {
        addProductToCart(id, 1L);
    }

    public void addProductToCart(Long id, Long amount) {
        addOrIncrementProduct(id, amount);
        onItemAmountChange();
    }

    public BigDecimal getSum() {
        BigDecimal totalSum = new BigDecimal(0);
        for (CartItemView product : getProductsInCart()) {
            BigDecimal productPrice = (product.getNewPrice() == null ?
                product.getPrice() : product.getNewPrice());
            BigDecimal oneSum = productPrice.multiply(new BigDecimal(product.getAmount()));
            totalSum = totalSum.add(oneSum);
        }
        return totalSum;
    }

    private void addOrIncrementProduct(Long id, Long amount) {
        for (CartItemView product : productsInCart) {
            if (product.getId().equals(id)) {
                product.setAmount(product.getAmount() + amount);
                return;
            }
        }
        CartItemView productToAdd = productHelper.getCartProductView(id);
        productToAdd.setAmount(amount);
        productsInCart.add(productToAdd);
    }

    public void onItemAmountChange() {
        saveUserCart();
    }

    public String buyProducts() {
        return "payment?faces-redirect=true";
    }

    public String buyWithoutLogin() {
        redirectAfterLogin = true;
        return "login?faces-redirect=true";
    }

    public List<CartItemView> getProductsInCart() {
        if (0 == productsInCart.size()) {
            CartView cart = productHelper.getCurrentUserCart();
            if (null == cart) {
                return productsInCart;
            }
            return productsInCart = cart.getItems();
        }

        return productsInCart;
    }

    private void saveUserCart() {
        CartView cart = new CartView();
        cart.setItems(productsInCart);
        CartView cartResult = productHelper.setCurrentUserCart(cart);
        if (null == cartResult) {
            return;
        }
        productsInCart.clear();
        productsInCart.addAll(cartResult.getItems());
    }

    public void clear() {
        productsInCart.clear();
        productHelper.currentUserClearCart();
    }

    public void onSuccessfulLogin() {
        //If not logged in user had products in cart, replace logged in users items with those.
        if (0 < productsInCart.size()) {
            saveUserCart();
            return;
        }
        productsInCart = productHelper.getCurrentUserCart().getItems();
    }
}
