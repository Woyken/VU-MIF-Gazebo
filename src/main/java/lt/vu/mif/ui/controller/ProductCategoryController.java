package lt.vu.mif.ui.controller;

import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.ui.helpers.interfaces.IProductHelper;
import lt.vu.mif.ui.view.ProductView;
import org.springframework.beans.factory.annotation.Autowired;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Getter
@Setter
@Named
@ViewScoped
public class ProductCategoryController {

    @Autowired
    private IProductHelper productHelper;
    private ProductView productView;

    private boolean addCategoryEnabled = false;
    private String currentCategory = "";
    private String newCategory = "";
    private String newAttribute = "";

    public void onPageLoad() {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) { return; }

        productView = productHelper.getProductViewFromNavigationQuery();
    }

    public void toggleAddCategory() {
        addCategoryEnabled = !addCategoryEnabled;
    }

    public void createNewCategory() {
        currentCategory = newCategory;
        toggleAddCategory();
    }

    public void discardNewCategory() {
        currentCategory = newCategory = "";
        toggleAddCategory();
    }

    public void createNewAttribute() {
        String test = newAttribute;
        newAttribute = "";
    }

    public void removeAttribute(int attributeId) {
        int a = 5;
    }
}