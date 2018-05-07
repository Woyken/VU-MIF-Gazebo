package lt.vu.mif.ui.controller;

import lt.vu.mif.ui.helpers.implementations.ProductHelper;
import lt.vu.mif.ui.view.ProductView;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;

@Named
@Getter
@Setter
@ViewScoped
public class AllProductsController {
    @Autowired
    private ProductHelper productHelper;

    private List<ProductView> products;
    private Long productToDeleteId;

    public void onPageLoad() {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            return;
        }

        getProducts();
    }

    private void getProducts() {
        products = productHelper.getAllProducts();
    }

    public String deleteProduct() {
        if (productToDeleteId == null) {
            throw new IllegalStateException("productToDelete was not set");
        }

        productHelper.deleteProductById(productToDeleteId);
        getProducts();

        return "admin-all-products?faces-redirect=true";
    }
}
