package lt.vu.mif.Controller;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import lombok.Setter;
import lt.vu.mif.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Named
@ViewScoped
public class AllProductsController {

    @Autowired
    private ProductRepository productRepository;

    @Setter
    private Long productToDeleteId;

    public void deleteProduct() {
        if (productToDeleteId == null) {
            throw new IllegalStateException("productToDelete was not set");
        }

        productRepository.deleteById(productToDeleteId);
    }
}
