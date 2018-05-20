package lt.vu.mif.ui.controller;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.ui.helpers.interfaces.ICategoryHelper;
import lt.vu.mif.ui.view.CategoryView;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
@Named
@ViewScoped
public class ProductCategoryController {
    @Autowired
    ICategoryHelper categoryHelper;

    private String newCategory;

    private Boolean isCreationSuccess;
    private String creationErrorMessage;

    private boolean addCategoryEnabled = false;
    private boolean isProductFound;
    private String currentCategory = "";
    private String newAttribute = "";

    public void onPageLoad() {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) { return; }
    }

    public void toggleAddCategory() {
        addCategoryEnabled = !addCategoryEnabled;
    }

    public void createNewCategory() {
        isCreationSuccess = false;
        creationErrorMessage = "";

        if (newCategory.isEmpty()) {
            creationErrorMessage = "Įveskite kategorijos pavadinimą";
            return;
        }

        if (categoryHelper.getCategoryByName(newCategory) != null) {
            creationErrorMessage = "Tokia kategorija jau egzistuoja";
            return;
        }

        CategoryView category = new CategoryView();
        category.setName(newCategory);
        categoryHelper.save(category);
        newCategory = "";
        isCreationSuccess = true;
    }

    public void discardNewCategory() {
        currentCategory = newCategory = "";
        toggleAddCategory();
    }

    public void createNewAttribute() {
        String test = newAttribute;
        newAttribute = "";
    }

    public void attachParentCategory() { int a = 5; }
    public void attachCategoryToProduct() { int a = 5; }

    public void removeAttribute(int attributeId) {
        int a = 5;
    }
    public void saveChanges() { int a = 5; }
}