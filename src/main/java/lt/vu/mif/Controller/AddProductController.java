package lt.vu.mif.Controller;

import java.util.stream.Collectors;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.Entity.Image;
import lt.vu.mif.Entity.Product;
import lt.vu.mif.Repository.ProductRepository;
import lt.vu.mif.View.ProductView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.RequestScope;

@Named
@Getter
@Setter
@RequestScope
public class AddProductController {

    @Autowired
    private ProductRepository productRepository;

    private ProductView productView = new ProductView();
    private boolean showSuccessMessage;

    public void saveProduct() {
        Product product = new Product();
        product.setDescription(productView.getDescription());
        product.setTitle(productView.getTitle());
        product.setPrice(productView.getPrice());
        product.setSku(productView.getSku());
        product.setNewPrice(productView.getNewPrice());
        product.getImages().addAll(productView.getImages().stream().map(image -> new Image(image.getBytes())).collect(Collectors.toList()));
        productRepository.save(product);
        showSuccessMessage = true;
    }
}