package lt.vu.mif.Controller;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.Entity.User;
import lt.vu.mif.Repository.ProductRepository;
import lt.vu.mif.Service.UserService;
import lt.vu.mif.View.ProductView;
import lt.vu.mif.View.UserView;
import org.springframework.beans.factory.annotation.Autowired;

@Named
@Getter
@Setter
@SessionScoped
public class ProductController implements Serializable {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserService userService;

    private UserView loggedUser;
    private List<ProductView> products;
    private String searchPhrase;

    public void onPageLoad() {
        
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            return;
        }

        User user = userService.getLoggedUser();
        loggedUser = user == null ? null : new UserView(user);

        products = productRepository.getAll().stream().map(ProductView::new)
                .collect(Collectors.toList());
    }

    public void searchProducts() {
        products = productRepository.getAll().stream().filter(p -> p.getTitle().contains(searchPhrase))
            .map(ProductView::new).collect(Collectors.toList());
    }
}