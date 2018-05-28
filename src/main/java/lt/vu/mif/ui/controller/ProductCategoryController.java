package lt.vu.mif.ui.controller;

import java.util.Collections;
import java.util.List;
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
    private List<CategoryView> categories;
    private CategoryView selectedCategory;

    private Boolean isCreationSuccess;
    private String creationErrorMessage;
    private Boolean isSavingSuccess;
    private String savingErrorMessage;

    public void onPageLoad() {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            return;
        }

        updateCategories();
    }

    private void updateCategories() {
        categories = categoryHelper.findAll();
        Collections.sort(categories);
    }

    public void createNewCategory() {
        eraseAllMessages();

        if (newCategory.isEmpty()) {
            creationErrorMessage = "Įveskite kategorijos pavadinimą";
            return;
        }

        for (CategoryView c : categories) {
            if (selectedCategory.equals(c.getParentCategory()) && newCategory.equals(c.getName())) {
                creationErrorMessage = "Tokia kategorija jau egzistuoja";
                return;
            }
        }

        CategoryView category = new CategoryView();
        category.setName(newCategory);
        category.setParentCategory(selectedCategory);
        categoryHelper.save(category);

        updateCategories();

        newCategory = "";
        isCreationSuccess = true;
    }

    private void eraseAllMessages() {
        isCreationSuccess = false;
        creationErrorMessage = "";
        isSavingSuccess = false;
        savingErrorMessage = "";
    }

}