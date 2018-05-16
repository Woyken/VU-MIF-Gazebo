package lt.vu.mif.ui.controller;


import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.ui.helpers.interfaces.IProductHelper;
import lt.vu.mif.ui.view.ProductView;
import org.springframework.beans.factory.annotation.Autowired;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@Getter
@Setter
@ViewScoped
public class ProductDiscountController {

    @Autowired
    private IProductHelper productHelper;
    private ProductView productView;

    private Boolean wasProductFound = true;
    private String discountAsPrice; // Because it can be a 59.99 (with a '.')
    private Integer discountAsPercent;


    public void onPageLoad() {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) { return; }

        // If navigation query doesn't have a valid product ID - it means we want to
        // create a discount either for a category or for all of the products
        try {
            productView = productHelper.getProductViewFromNavigationQuery();
            wasProductFound = true;
        } catch (Exception x) {
            productView = new ProductView();
            wasProductFound = false;
        }
    }

    public void addDiscount() {
        int a = 5;
    }
}