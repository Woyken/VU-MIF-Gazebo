package lt.vu.mif.ui.controller;

import lt.vu.mif.ui.helpers.interfaces.IProductHelper;
import lt.vu.mif.ui.view.ProductView;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
@Named
@ViewScoped
public class ProductDetailsController {

    @Autowired
    private IProductHelper productHelper;

    private ProductView productView;
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
    }
}