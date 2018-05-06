package lt.vu.mif.Controller;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.Entity.Product;
import lt.vu.mif.Repository.ImageRepository;
import lt.vu.mif.Repository.ProductRepository;
import lt.vu.mif.View.ProductView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
@Named
@ViewScoped
public class ProductDetailsController {
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private ProductRepository productRepository;

    private ProductView productView;

    boolean showSuccessMessage = false;

    public void onPageLoad() {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            return;
        }

        String productId  = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("productId");
        if (StringUtils.isBlank(productId)) {
            throw new IllegalArgumentException("Invalid request parameter");
        }

        Product entity = productRepository.get(Long.valueOf(productId));

        if (entity == null) {
            throw new IllegalStateException("Product" + "with ID=" + productId +  "not found");
        }
        productView = new ProductView(entity);
    }
}