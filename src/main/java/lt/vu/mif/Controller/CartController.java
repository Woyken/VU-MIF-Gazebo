package lt.vu.mif.Controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.Repository.ProductRepository;
import lt.vu.mif.Service.UserService;
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

    //private List<ProductView> productsInCart = new ArrayList<>();

    private Map<ProductView, Long> productsInCart = new HashMap<>();

    public void addProductToCart(Long id) {
        addOrIncrementProduct(id);
    }

    private void addOrIncrementProduct(Long id) {
        Optional<ProductView> existingProductInCart = productsInCart.keySet()
            .stream()
            .filter(key -> key.getId().equals(id))
            .findFirst();
        if (!existingProductInCart.isPresent()) {
            List<Long> idsList = new ArrayList<>();
            idsList.add(id);
            ProductView productToAdd = new ProductView(productRepository.get(idsList).get(0));
            productsInCart.put(productToAdd, 1L);
        } else {
            productsInCart.put(
                existingProductInCart.get(),
                productsInCart.get(existingProductInCart.get()) + 1);
        }
    }
}
