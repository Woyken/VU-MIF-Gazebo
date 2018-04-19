package lt.vu.mif.Controller;

import java.io.Serializable;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.Entity.User;
import lt.vu.mif.Repository.ProductRepository;
import lt.vu.mif.Search.ProductSearch;
import lt.vu.mif.Service.UserService;
import lt.vu.mif.Utils.Paging;
import lt.vu.mif.View.ProductView;
import lt.vu.mif.View.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

@Named
@Getter
@Setter
@ViewScoped
public class ProductController implements Serializable {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserService userService;

    private UserView loggedUser;

    private ProductSearch productSearch = new ProductSearch();

    private Page<ProductView> productsPage;
    private Paging paging;

    public void onPageLoad() {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            return;
        }

        User user = userService.getLoggedUser();
        loggedUser = user == null ? null : new UserView(user);

        resetProductSearch();
        searchProducts();
        paging = new Paging(productsPage.getTotalPages());
    }

    public void searchProducts() {
        productsPage = productRepository.getProductsPage(productSearch).map(ProductView::new);
    }

    private void resetProductSearch() {
        productSearch.setMaxPrice(null);
        productSearch.setMinPrice(null);
        productSearch.setTitle(null);
        paging.resetActivePage();
        paging.setTotalPages(productsPage.getTotalPages());
    }

    public void next() {
        paging.next();
        searchProducts();
    }

    public void previous() {
        paging.previous();
        searchProducts();
    }

    public void searchProducts(int activePage) {
        productSearch.setActivePage(activePage);
        paging.setActivePage(activePage);
        searchProducts();
    }

    public int getPagingIndex() {
        return paging.getIndex();
    }
}