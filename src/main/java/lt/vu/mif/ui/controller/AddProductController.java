package lt.vu.mif.ui.controller;

import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.ui.helpers.implementations.ProductHelper;
import lt.vu.mif.ui.view.ProductView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.RequestScope;

@Named
@Getter
@Setter
@RequestScope
public class AddProductController {

    @Autowired
    private ProductHelper productHelper;

    private ProductView productView = new ProductView();
    private boolean showSuccessMessage;

    public void saveProduct() {
        productHelper.createNewProduct(productView);
        showSuccessMessage = true;
    }
}