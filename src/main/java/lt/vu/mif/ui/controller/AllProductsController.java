package lt.vu.mif.ui.controller;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import lombok.Setter;
import lt.vu.mif.ui.helpers.interfaces.IProductHelper;
import org.springframework.beans.factory.annotation.Autowired;

@Named
@ViewScoped
public class AllProductsController {
    @Autowired
    private IProductHelper productHelper;

    @Setter
    private Long productToDeleteId;

    public void deleteProduct() {
        if (productToDeleteId == null) {
            throw new IllegalStateException("productToDelete was not set");
        }

        productHelper.deleteById(productToDeleteId);
    }
}
