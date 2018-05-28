package lt.vu.mif.ui.controller;

import java.util.ArrayList;
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
    private List<CategoryView> categoriesWithEmpty;
    private CategoryView selectedCategory;
    private CategoryView parentCategory;
    private CategoryView emptyCategory;

    private Boolean isCreationSuccess;
    private String creationErrorMessage;
    private Boolean isSavingSuccess;
    private String savingErrorMessage;

    public void onPageLoad() {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            return;
        }

        emptyCategory = new CategoryView();
        emptyCategory.setName("");
        updateCategories();
    }

    private void updateCategories() {
        categories = categoryHelper.findAll();
        Collections.sort(categories);
        categoriesWithEmpty = new ArrayList<>(categories);
        categoriesWithEmpty.add(0, emptyCategory);
    }

    public void categoryChange() {
        parentCategory = selectedCategory.getParentCategory();
    }

    public void createNewCategory() {
        eraseAllMessages();

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

        updateCategories();

        newCategory = "";
        isCreationSuccess = true;
    }

    public void saveChanges() {
        eraseAllMessages();

        if (selectedCategory == null) {
            savingErrorMessage = "Nepasirinkote kategorijos";
            return;
        }

        if (selectedCategory.equals(parentCategory)) {
            savingErrorMessage = "Negalima kategorijos priskirti sau pačiai kaip tėvinės";
            return;
        }

        if (parentCategory != null && isCategoryLoop(selectedCategory, parentCategory) == true) {
            savingErrorMessage = "Pasirinkta tėvinė kategorija yra tarp keičiamos kategorijos vaikų";
            return;
        }

        selectedCategory.setParentCategory(parentCategory);
        categoryHelper.update(selectedCategory);

        updateCategories();

        isSavingSuccess = true;
    }

    private void eraseAllMessages() {
        isCreationSuccess = false;
        creationErrorMessage = "";
        isSavingSuccess = false;
        savingErrorMessage = "";
    }

    //Checks if new parent assignment would make a category loop
    private boolean isCategoryLoop(CategoryView selected, CategoryView potentialParent) {
        CategoryView parentsParent = potentialParent.getParentCategory();

        if (parentsParent == null) {
            return false;
        }

        if (parentsParent.equals(selected)) {
            return true;
        }

        return isCategoryLoop(selected, parentsParent);
    }


}