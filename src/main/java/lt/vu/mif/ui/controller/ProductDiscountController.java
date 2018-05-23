package lt.vu.mif.ui.controller;


import java.util.Collections;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.ui.helpers.interfaces.ICategoryHelper;
import lt.vu.mif.ui.helpers.interfaces.IProductHelper;
import lt.vu.mif.ui.view.CategoryView;
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
    @Autowired
    private ICategoryHelper categoryHelper;

    private ProductView productView;
    private DiscountView discountView;
    private CategoryView selectedCategory;
    private List<CategoryView> categories;

    private Boolean isProductFound;
    private Boolean isSuccess;

    private String errorMessage;

    public void onPageLoad() {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            return;
        }

        try {
            productView = productHelper.getProductViewFromNavigationQuery();
            discountView = productView.getDiscount() == null ? new DiscountView() :
                productView.getDiscount();
            isProductFound = true;
        } catch (IllegalArgumentException x) {
            // If navigation query doesn't have a valid product ID - it means we want to
            // create a discount either for a category or for all of the products
            categories = categoryHelper.findAll();
            Collections.sort(categories);
            isProductFound = false;
        }

    }

    public void categoryChange() {
        discountView = selectedCategory.getDiscount() == null ? new DiscountView() :
            selectedCategory.getDiscount();
    }

    public void addDiscount() {
        if (isProductFound) {
            addDiscountToProduct();
        }

        addDiscountToCategory();
    }

    public void removeDiscount() {
        discountView = new DiscountView();

        if (isProductFound) {
            removeDiscountFromProduct();
        }

        removeDiscountFromCategory();
    }

    private void addDiscountToProduct() {
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

    private void addDiscountToCategory() {
        selectedCategory.setDiscount(discountView);
        categoryHelper.update(selectedCategory);

        isSuccess = true;

        return;
    }

    private void removeDiscountFromProduct() {
        productView.setDiscount(null);
        productHelper.update(productView);
    }

    private void removeDiscountFromCategory() {
        selectedCategory.setDiscount(null);
        categoryHelper.update(selectedCategory);
    }

}