package lt.vu.mif.ui.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.Part;
import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.model.product.Category;
import lt.vu.mif.model.product.Discount;
import lt.vu.mif.ui.helpers.interfaces.IProductHelper;
import lt.vu.mif.ui.view.ImageInMemoryStreamer;
import lt.vu.mif.ui.view.ImageView;
import lt.vu.mif.ui.view.ProductView;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
@ViewScoped
@Named
public class ProductEditController {

    @Autowired
    private IProductHelper productHelper;

    private ImageInMemoryStreamer imageInMemoryStreamer = new ImageInMemoryStreamer();
    private ProductView productView;
    private Part uploadedFile;
    private boolean showSuccessMessage;
    private boolean isProductFound;

    // TODO: Remove this when product uses real categories
    private Category category;

    private List<ImageView> newImages = new ArrayList<>();

    public void onPageLoad() {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) { return; }

        // If a product with passed in ID is not found -
        // it means we want to add a new product
        try {
            productView = productHelper.getProductViewFromNavigationQuery();
            isProductFound = true;
        } catch (Exception x) {
            productView = new ProductView();
            isProductFound = false;
        }

        // TODO: Remove this when product uses real categories
        category = new Category();
        category.setName("UI Test ONLY");

        showSuccessMessage = false;
    }

    public void handleUploadedFile() throws Exception {
        ImageView imageView = new ImageView();
        imageView.setId(ThreadLocalRandom.current().nextLong(Long.MIN_VALUE, 0));
        imageView.setBytes(IOUtils.toByteArray(uploadedFile.getInputStream()));

        imageInMemoryStreamer.addImage(imageView.getId(), uploadedFile.getInputStream());
        newImages.add(imageView);
    }

    public void saveChanges() {
        productView.getImages().addAll(newImages);
        productHelper.update(productView);
        showSuccessMessage = true;
        // NOTE: YOU MUST UPDATE PRODUCT VIEW INFORMATION
        // (OR AT LEAST IT'S IMAGES BECAUSE IMAGE IS FETCHED BY ID AND IMAGE ID IS CHANGED WHEN SAVED TO DB)
        productView = productHelper.getProduct(productView.getId());
        newImages.clear();
        imageInMemoryStreamer.getImagesInMemory().clear();
    }

    public void removeImage(ImageView imageView) {
        productView.getImages().remove(imageView);
        newImages.remove(imageView);
    }
}
