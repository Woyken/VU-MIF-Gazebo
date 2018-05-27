package lt.vu.mif.ui.controller;

import java.util.ArrayList;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.ui.helpers.interfaces.IProductHelper;
import org.springframework.beans.factory.annotation.Autowired;

@Named
@ViewScoped
public class AllProductsController {
    @Autowired
    private IProductHelper productHelper;

    @Getter
    @Setter
    private boolean multipleDeleteEnabled = false;

    @Setter
    private List<Long> productsToDeleteIds = new ArrayList<>();


    public void addProductToDelete(Long productId) {
        // The first item was checked - enable multiple delete
        if (productsToDeleteIds.isEmpty()) {
            multipleDeleteEnabled = true;
        }

        // If the same ID was passed a second time - the checkbox was unchecked
        if (productsToDeleteIds.contains(productId)) {
            productsToDeleteIds.remove(productId);
        } else {
            productsToDeleteIds.add(productId);
        }

        // All checkboxes were unselected - disable multiple delete
        if (productsToDeleteIds.isEmpty()) {
            multipleDeleteEnabled = false;
        }
    }

    public String deleteMultipleProducts() {
        if (productsToDeleteIds.isEmpty()) {
            return null;
        }

        productHelper.deleteMultipleByIds(productsToDeleteIds);
        multipleDeleteEnabled = false;
        productsToDeleteIds.clear();
        return "all-products?faces-redirect=true";
    }
}
