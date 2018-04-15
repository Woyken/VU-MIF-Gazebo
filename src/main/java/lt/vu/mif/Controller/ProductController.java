package lt.vu.mif.Controller;

import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.Entity.User;
import lt.vu.mif.Repository.ProductRepository;
import lt.vu.mif.Service.UserService;
import lt.vu.mif.View.ProductView;
import lt.vu.mif.View.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

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

    @Setter
    public String searchPhrase;

    public void onPageLoad() {
        User user = userService.getLoggedUser();
        loggedUser = user == null ? null : new UserView(user);

        if(null == products) {
            products = productRepository.getAll().stream().map(ProductView::new)
                .collect(Collectors.toList());
        }
    }

    public void searchProducts() {
        products = productRepository.getAll().stream().filter(p -> p.getTitle().contains(searchPhrase))
            .map(ProductView::new).collect(Collectors.toList());
    }
}