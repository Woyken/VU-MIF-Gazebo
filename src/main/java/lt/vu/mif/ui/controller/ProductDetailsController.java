package lt.vu.mif.ui.controller;

import java.util.ArrayList;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.Logging.Logged;
import lt.vu.mif.ui.helpers.interfaces.IProductHelper;
import lt.vu.mif.ui.view.AttributeView;
import lt.vu.mif.ui.view.CategoryView;
import lt.vu.mif.ui.view.ProductView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

@Logged
@Getter
@Setter
@Named
@ViewScoped
public class ProductDetailsController {

    @Autowired
    private IProductHelper productHelper;

    private ProductView productView;
    private List<AttributeView> attributes;
    boolean showSuccessMessage = false;

    public void onPageLoad() {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            return;
        }

        String productId = FacesContext.getCurrentInstance().getExternalContext()
            .getRequestParameterMap().get("productId");
        if (StringUtils.isBlank(productId)) {
            throw new IllegalArgumentException("Invalid request parameter");
        }

        productView = productHelper.getProduct(Long.valueOf(productId));

        if (productView == null) {
            throw new IllegalStateException("Product" + "with ID=" + productId + "not found");
        }

        attributes = new ArrayList<>();
        CategoryView category = productView.getCategory();

        while (category != null) {
            for (AttributeView a : category.getAttributes()) {
                attributes.add(a);
            }

            category = category.getParentCategory();
        }
    }

    public void onBeforeValidatorAddToCart() {
        showSuccessMessage = false;
    }

    public void onAddToCart() {
        showSuccessMessage = true;
    }
}