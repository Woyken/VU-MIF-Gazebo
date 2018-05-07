package lt.vu.mif.ui.controller;

import lt.vu.mif.ui.helpers.implementations.ProductHelper;
import lt.vu.mif.ui.view.ProductView;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;

@Named
@ViewScoped
public class AllProductsController {
    @Autowired
    private ProductHelper productHelper;

    @Setter
    private Long productToDeleteId;

    public void deleteProduct() {
        if (productToDeleteId == null) {
            throw new IllegalStateException("productToDelete was not set");
        }

        productRepository.deleteById(productToDeleteId);
    }
}
