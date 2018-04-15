package lt.vu.mif.Controller;

import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.Entity.Image;
import lt.vu.mif.Entity.Product;
import lt.vu.mif.Repository.ProductRepository;
import lt.vu.mif.View.ImageView;
import lt.vu.mif.View.ProductView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Named
public class ProductEditController {
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
