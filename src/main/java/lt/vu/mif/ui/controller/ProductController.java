package lt.vu.mif.ui.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.Logging.Logged;
import lt.vu.mif.ui.helpers.interfaces.ICategoryHelper;
import lt.vu.mif.ui.helpers.interfaces.IProductHelper;
import lt.vu.mif.ui.paging.Paging;
import lt.vu.mif.ui.view.AttributeValue;
import lt.vu.mif.ui.view.AttributeView;
import lt.vu.mif.ui.view.CategoryView;
import lt.vu.mif.ui.view.ProductSearchView;
import lt.vu.mif.ui.view.ProductView;
import lt.vu.mif.utils.constants.Constants;
import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Logged
@Named
@Getter
@Setter
@ViewScoped
@Component
public class ProductController implements Serializable {

    @Autowired
    private IProductHelper productHelper;
    @Autowired
    private ICategoryHelper categoryHelper;

    private String minPrice;
    private String maxPrice;

    private List<AttributeView> attributes = new ArrayList<>();

    private ProductSearchView productSearch = new ProductSearchView();
    private Page<ProductView> productsPage;
    private Integer pageSize = Constants.PAGE_SIZE;
    private Paging paging = new Paging();

    public void onPageLoad() {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            return;
        }

        resetProductSearch();
        searchProducts();
    }

    public void searchProducts() {
        paging.reset();
        search();
        paging.setTotalPages(productsPage.getTotalPages());
    }

    public void searchPrice() {
        productSearch.setMinPrice(minPrice.isEmpty() ? null : new BigDecimal(minPrice));
        productSearch.setMaxPrice(maxPrice.isEmpty() ? null : new BigDecimal(maxPrice));

        searchProducts();
    }

    public void searchCategory(TreeNode categoryNode) {
        makeAttributeCategoryList((CategoryView) categoryNode.getData());

        List<CategoryView> categoriesToSearch = new ArrayList<CategoryView>();
        makeCategoryList(categoriesToSearch, categoryNode);

        productSearch.setCategories(categoriesToSearch);

        searchProducts();
    }

    private void makeAttributeCategoryList(CategoryView category) {
        attributes = new ArrayList<>();

        while (category != null) {
            for (AttributeView a : category.getAttributes()) {
                attributes.add(a);
            }

            category = category.getParentCategory();
        }
    }

    private void makeCategoryList(List<CategoryView> categories, TreeNode current) {
        categories.add((CategoryView) current.getData());

        for (TreeNode t : current.getChildren()) {
            makeCategoryList(categories, t);
        }
    }

    public void searchAttributes() {
        List<AttributeValue> attributeValues = new ArrayList<>();

        for (AttributeView a : attributes) {
            for (AttributeValue v : a.getValues()) {
                if (v.getIsSelected()) {
                    attributeValues.add(v);
                }
            }
        }

        productSearch.setAttributeValues(attributeValues);

        searchProducts();
    }

    public void search() {
        productsPage = productHelper
            .getProductsPage(paging.getActivePage(), pageSize != null ? pageSize : paging.getPageSize(), productSearch);
        paging.setTotalPages(productsPage.getTotalPages());
    }

    private void resetProductSearch() {
        productSearch.reset();
        paging.reset();
    }

    public void next() {
        paging.next();
        search();
    }

    public void previous() {
        paging.previous();
        search();
    }

    public void searchProducts(int activePage) {
        paging.setActivePage(activePage);
        productsPage = productHelper
            .getProductsPage(paging.getActivePage(), pageSize != null ? pageSize : paging.getPageSize(), productSearch)
            .map(ProductView::new);
    }

    public int getPagingIndex() {
        return paging.getIndex();
    }

}