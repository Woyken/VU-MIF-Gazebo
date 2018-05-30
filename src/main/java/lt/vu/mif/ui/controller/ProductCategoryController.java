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
import lt.vu.mif.utils.constants.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;

@Logged
@Getter
@Setter
@Named
@ViewScoped
public class ProductCategoryController {

    @Autowired
    private ICategoryHelper categoryHelper;

    private CategoryView newCategory = new CategoryView();
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
        findRootCategory();

        newCategory.setAttributes(new ArrayList<>());
        newCategory.getAttributes().add(new AttributeView());

        newCategory.getAttributes().get(0).setValues(new ArrayList<>());
        newCategory.getAttributes().get(0).getValues().add(new AttributeValue());
    }

    private void updateCategories() {
        categories = categoryHelper.findAll();
        Collections.sort(categories);
    }

    private void findRootCategory() {
        for (CategoryView categoryView : categories) {
            if (categoryView.getName().equals(Constants.ROOT_CATEGORY_NAME)) {
                selectedCategory = categoryView;
                break;
            }
        }
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
        view.getValues().add(new AttributeValue());
        newCategory.getAttributes().add(view);
    }

    public void removeNewAttribute(AttributeView view) {
        if (newCategory.getAttributes().size() != 1) {
            newCategory.getAttributes().remove(view);
        }
    }

    public void addNewAttributeValue(AttributeView view, AttributeValue value) {
        view.getValues().add(value);
    }

    public void removeNewAttributeValue(AttributeView view, AttributeValue value) {
        if (view.getValues().size() != 1) {
            view.getValues().remove(value);
        }
    }

    public void saveNewCategory() {
        categoryHelper.save(newCategory);
    }

}