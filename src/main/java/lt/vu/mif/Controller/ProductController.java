package lt.vu.mif.Controller;

import lombok.Getter;
import lt.vu.mif.Entity.User;
import lt.vu.mif.Repository.ProductRepository;
import lt.vu.mif.Service.UserService;
import lt.vu.mif.View.ProductView;
import lt.vu.mif.View.UserView;

import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;
import java.util.List;
import java.util.stream.Collectors;

@Named
@Getter
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
}