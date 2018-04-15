package lt.vu.mif.Controller;

import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.Entity.Product;
import lt.vu.mif.Repository.ImageRepository;
import lt.vu.mif.Repository.ProductRepository;
import lt.vu.mif.View.ProductView;

@Getter
@Setter
@Named
public class ProductDetailsController {
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private ProductRepository productRepository;

    private ProductView productView;

    public void onPageLoad() {
        String productId  = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("productId");
        if (StringUtils.isBlank(productId)) {
            throw new IllegalArgumentException("Invalid request parameter");
        }

        Product entity = productRepository.get(Product.class, Long.valueOf(productId));

        if (entity == null) {
            throw new IllegalStateException("Product" + "with ID=" + productId +  "not found");
        }
        productView = new ProductView(entity);
    }
}