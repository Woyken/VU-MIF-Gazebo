package lt.vu.mif.Controller;

import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Named;
import lombok.Getter;
import lt.vu.mif.Entity.User;
import lt.vu.mif.Repository.ProductRepository;
import lt.vu.mif.Service.UserService;
import lt.vu.mif.View.ProductView;
import lt.vu.mif.View.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
s
@Named
@Getter
@Controller
public class ProductController {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserService userService;

    private UserView loggedUser;

    private List<ProductView> products;

    public void onPageLoad() {
        User user = userService.getLoggedUser();
        loggedUser = user == null ? null : new UserView(user);

        products = productRepository.getAll().stream().map(ProductView::new)
            .collect(Collectors.toList());
    }

    public List<ProductView> searchProducts(String input) throws IllegalArgumentException {
        if (input.isEmpty()) {
            throw new IllegalArgumentException("The search bar is empty");
        }

        return productRepository.getAll().stream().filter(p -> p.getTitle().contains(input))
            .map(ProductView::new).collect(Collectors.toList());
    }
}