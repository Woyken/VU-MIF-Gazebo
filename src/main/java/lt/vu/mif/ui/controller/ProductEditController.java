package lt.vu.mif.ui.controller;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.ui.helpers.interfaces.IProductHelper;
import lt.vu.mif.ui.view.ProductView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
@Named
@ViewScoped
public class ProductEditController {
    @Autowired
    private IProductHelper productHelper;

    private ProductView productView;

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

    public void saveChanges() {
//        Product product = new Product();
//        List<Image> images = new ArrayList<Image>();
//        List<ImageView> viewImages = productView.getImages();
//        for (int i = 0; i < viewImages.size(); i++)
//        {
//            ImageView current = viewImages.get(i);
//            Image img = new Image();
//            img.setId(current.getId());
//            img.setContent(current.getContent());
//            images.add(img);
//        }
//
//        product.setId(productView.getId());
//        product.setSku(productView.getSku());
//        product.setTitle(productView.getTitle());
//        product.setPrice(productView.getPrice());
//        product.setDescription(productView.getDescription());
//        product.setImages(viewImages);
//        productRepository.save(product);
    }
}
