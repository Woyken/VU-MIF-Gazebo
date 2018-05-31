package lt.vu.mif.ui.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.Logging.Logged;
import lt.vu.mif.ui.helpers.interfaces.ICategoryHelper;
import lt.vu.mif.ui.view.AttributeValue;
import lt.vu.mif.ui.view.AttributeView;
import lt.vu.mif.ui.view.CategoryView;
import org.springframework.beans.factory.annotation.Autowired;

@Logged
@Getter
@Setter
@Named
@ViewScoped
public class ProductCategoryController {

    @Autowired
    private ICategoryHelper categoryHelper;

    private String newCategory;
    private List<CategoryView> categories;
    private CategoryView selectedCategory;

    private Boolean isCreationSuccess;
    private String creationErrorMessage;
    private Boolean isSavingSuccess;
    private String savingErrorMessage;

    private String categoryAttributeName;


    public void onPageLoad() {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            return;
        }

        updateCategories();

//        newCategory.setAttributes(new ArrayList<>());
//        newCategory.getAttributes().add(new AttributeView());
//
//        newCategory.getAttributes().get(0).setValues(new ArrayList<>());
//        newCategory.getAttributes().get(0).getValues().add(new AttributeValue());
    }

    private void updateCategories() {
        categories = categoryHelper.findAll();
        Collections.sort(categories);
    }

    //I know it is not really good to instantly save to database, but can't do it differently now
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
        CategoryView saved = categoryHelper.save(category);
        categories.add(saved);
        Collections.sort(categories);
        selectedCategory = saved;

        updateCategories();

        newCategory = "";
        isCreationSuccess = true;
    }

    public void deleteCategory() {
        eraseAllMessages();

        if (selectedCategory.getParentCategory() == null) {
            savingErrorMessage = "Negalima ištrinti šakninės kategorijos";
            return;
        }

        deleteCategory(selectedCategory);
        updateCategories();
        //Should always be at least 1 category
        selectedCategory = categories.get(0);
    }

    //Inefficient, but optimization unimportant right now
    private void deleteCategory(CategoryView category) {
        //Delete all children
        for (CategoryView c : categories) {
            if (category.equals(c.getParentCategory())) {
                deleteCategory(c);
            }
        }

        categoryHelper.delete(category);
    }

    private void eraseAllMessages() {
        isCreationSuccess = false;
        creationErrorMessage = "";
        isSavingSuccess = false;
        savingErrorMessage = "";
    }

    public void addNewAttribute() {
        AttributeView view = new AttributeView();
        view.setValues(new ArrayList<>());
        selectedCategory.getAttributes().add(view);
    }

    public void removeNewAttribute(AttributeView view) {
        selectedCategory.getAttributes().remove(view);
    }

    public void addNewAttributeValue(AttributeView view) {
        view.getValues().add(new AttributeValue());
    }

    public void removeNewAttributeValue(AttributeView view, AttributeValue value) {
        view.getValues().remove(value);
    }

    public void updateCategory() {
        eraseAllMessages();

        for (CategoryView c : categories) {
            categoryHelper.update(c);
        }

        isSavingSuccess = true;
    }

}