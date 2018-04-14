package lt.vu.mif.Controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.Repository.ProductRepository;
import lt.vu.mif.Service.UserService;
import lt.vu.mif.View.CartProductView;
import lt.vu.mif.View.ProductView;
import org.springframework.beans.factory.annotation.Autowired;

@Named
@Getter
@Setter
@SessionScoped
public class CartController implements Serializable {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserService userService;

    private List<CartProductView> productsInCart = new ArrayList<>();
    CartProductView productSelectedForRemoval;

    public void selectForRemoval(CartProductView product) {
        productSelectedForRemoval = product;
    }

    public void removeSelectedProduct() {
        if (null != productSelectedForRemoval) {
            productsInCart.remove(productSelectedForRemoval);
        }
    }

    public void addProductToCart(Long id) {
        addOrIncrementProduct(id);
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

    private void addOrIncrementProduct(Long id) {
        for (CartProductView product : productsInCart) {
            if (product.getId().equals(id)) {
                product.setAmount(product.getAmount() + 1);
                return;
            }
        }
        List<Long> idsList = new ArrayList<>();
        idsList.add(id);
        CartProductView productToAdd = new CartProductView(
            new ProductView(productRepository.get(idsList).get(0)));
        productToAdd.setAmount(1L);
        productsInCart.add(productToAdd);
    }
}
