package lt.vu.mif.ui.controller;


import java.math.BigDecimal;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.ui.helpers.interfaces.IProductHelper;
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

    private Boolean isProductFound;
    private BigDecimal discountAsPrice;
    //BigDecimal type for validation purposes
    private BigDecimal discountAsPercent;
    private String startDate;
    private String startTime;
    private String endDate;
    private String endTime;

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
        }
    }

    public void addDiscount() {

    }
}