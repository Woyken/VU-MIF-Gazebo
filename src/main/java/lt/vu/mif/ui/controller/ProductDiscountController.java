package lt.vu.mif.ui.controller;


import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.ui.helpers.interfaces.IProductHelper;
import lt.vu.mif.ui.view.DiscountView;
import lt.vu.mif.ui.view.ProductView;
import org.springframework.beans.factory.annotation.Autowired;

@Named
@Getter
@Setter
@ViewScoped
public class ProductDiscountController {

    @Autowired
    private IProductHelper productHelper;
    private ProductView productView;
    private DiscountView discountView;

    private Boolean isProductFound;
    private Boolean isSuccess;

    private String errorMessage;

    public void onPageLoad() {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            return;
        }

        try {
            productView = productHelper.getProductViewFromNavigationQuery();
            isProductFound = true;
        } catch (IllegalArgumentException x) {
            // If navigation query doesn't have a valid product ID - it means we want to
            // create a discount either for a category or for all of the products
            productView = new ProductView();
            isProductFound = false;
            return;
        }

        discountView = productView.getDiscount() == null ? new DiscountView() :
            productView.getDiscount();
    }

    public void addDiscount() {
        //Couldn't access product in validation class, so have to do it here
        if (discountView.getAbsoluteDiscount() != null &&
            discountView.getAbsoluteDiscount().compareTo(productView.getPrice()) == 1) {
            errorMessage = "Nuolaidos kaina negali būti didesnė už įprastą produkto kainą";
            return;
        }

        productView.setDiscount(discountView);
        productHelper.update(productView);

        isSuccess = true;

        return;
    }

    public void removeDiscount() {
        discountView = new DiscountView();
        productView.setDiscount(null);
        productHelper.update(productView);
    }

}