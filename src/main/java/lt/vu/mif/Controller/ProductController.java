package lt.vu.mif.Controller;

import java.io.Serializable;
import java.math.BigDecimal;
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

    private String minPrice;
    private String maxPrice;

    private UserView loggedUser;

    private ProductSearch productSearch = new ProductSearch();

    private Page<ProductView> productsPage;
    private Paging paging = new Paging();

    public void onPageLoad() {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            return;
        }

        User user = userService.getLoggedUser();
        loggedUser = user == null ? null : new UserView(user);

        resetProductSearch();
        searchProducts();
    }

    public void searchProducts() {
        paging.reset();
        search();
        paging.setTotalPages(productsPage.getTotalPages());
    }

    public void searchPrice() {
        productSearch.setMinPrice(minPrice.isEmpty() ? null : new BigDecimal(minPrice));
        productSearch.setMaxPrice(maxPrice.isEmpty() ? null : new BigDecimal(maxPrice));

        searchProducts();
    }

    private void search() {
        productsPage = productRepository.getProductsPage(productSearch, paging).map(ProductView::new);
        paging.setTotalPages(productsPage.getTotalPages());
    }

    private void resetProductSearch() {
        productSearch.reset();
        paging.reset();
    }

    public void next() {
        paging.next();
        search();
    }

    public void previous() {
        paging.previous();
        search();
    }

    public void searchProducts(int activePage) {
        paging.setActivePage(activePage);
        productsPage = productRepository.getProductsPage(productSearch, paging).map(ProductView::new);
    }

    public int getPagingIndex() {
        return paging.getIndex();
    }

}