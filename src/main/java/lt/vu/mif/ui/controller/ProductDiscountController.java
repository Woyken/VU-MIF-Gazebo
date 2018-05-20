package lt.vu.mif.ui.controller;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.ui.helpers.interfaces.IProductHelper;
import lt.vu.mif.ui.view.DiscountView;
import lt.vu.mif.ui.view.ProductView;
import lt.vu.mif.utils.validation.ValidationUtils;
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
    private Boolean isSuccess;

    private String errorMessage;

    private BigDecimal discountAsPrice;
    //BigDecimal type for validation purposes
    private BigDecimal discountAsPercent;
    //Can't set LocalDateTime fields with jsf, so using Strings
    //(a nicer way would have been to use jsf custom converter)
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
            return;
        }

        if (productView.getDiscount() == null) {
            return;
        }

        discountAsPrice = productView.getDiscount().getAbsoluteDiscount();
        if (productView.getDiscount().getPercentageDiscount() != null) {
            discountAsPercent = new BigDecimal(productView.getDiscount().getPercentageDiscount());
        }
        startDate = productView.getDiscount().getFrom()
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        endDate = productView.getDiscount().getTo()
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        startTime = productView.getDiscount().getFrom()
            .format(DateTimeFormatter.ofPattern("HH:mm"));
        endTime = productView.getDiscount().getTo().format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public void addDiscount() {
        //Couldn't access product in validation class, so have to do it here
        if (discountAsPrice != null && discountAsPrice.compareTo(productView.getPrice()) == 1) {
            errorMessage = "Nuolaidos kaina negali būti didesnė už įprastą produkto kainą";
            return;
        }

        DiscountView discountView = new DiscountView();
        discountView.setAbsoluteDiscount(discountAsPrice);
        if (discountAsPercent != null) {
            discountView.setPercentageDiscount(discountAsPercent.longValue());
        }
        discountView.setFrom(LocalDateTime.parse(startDate + " " + startTime,
            DateTimeFormatter.ofPattern(ValidationUtils.DATETIME_FORMAT)));
        discountView.setTo(LocalDateTime.parse(endDate + " " + endTime,
            DateTimeFormatter.ofPattern(ValidationUtils.DATETIME_FORMAT)));

        productView.setDiscount(discountView);
        productHelper.update(productView);

        isSuccess = true;

        return;
    }

    public void removeDiscount() {
        discountAsPrice = null;
        discountAsPercent = null;
        startDate = "";
        startTime = "";
        endDate = "";
        endTime = "";

        productView.setDiscount(null);
        productHelper.update(productView);
    }

}