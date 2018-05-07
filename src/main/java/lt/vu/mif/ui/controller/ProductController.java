package lt.vu.mif.Controller;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.ui.helpers.interfaces.IProductHelper;
import lt.vu.mif.ui.paging.Paging;
import lt.vu.mif.ui.view.ProductView;
import lt.vu.mif.utils.search.ProductSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Named
@Getter
@Setter
@ViewScoped
@Component
public class ProductController implements Serializable {

    @Autowired
    private IProductHelper productHelper;

    private String minPrice;
    private String maxPrice;
    private ProductSearch productSearch = new ProductSearch();
    private Page<ProductView> productsPage;
    private Paging paging = new Paging();

    public void onPageLoad() {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            return;
        }

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

    public void search() {
        productsPage = productHelper
            .getProductsPage(paging.getActivePage(), paging.getPageSize(), productSearch);
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
        productsPage = productHelper
            .getProductsPage(paging.getActivePage(), paging.getPageSize(), productSearch)
            .map(ProductView::new);
    }

    public int getPagingIndex() {
        return paging.getIndex();
    }

}