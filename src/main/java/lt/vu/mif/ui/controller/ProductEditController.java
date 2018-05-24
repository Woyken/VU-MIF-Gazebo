package lt.vu.mif.ui.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.Part;
import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.ui.helpers.interfaces.ICategoryHelper;
import lt.vu.mif.ui.helpers.interfaces.IImageHelper;
import lt.vu.mif.ui.helpers.interfaces.IProductHelper;
import lt.vu.mif.ui.view.CategoryView;
import lt.vu.mif.ui.view.ImageInMemoryStreamer;
import lt.vu.mif.ui.view.ImageView;
import lt.vu.mif.ui.view.ProductView;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

@Getter
@Setter
@ViewScoped
@Named
public class ProductEditController {

    @Autowired
    private IProductHelper productHelper;
    @Autowired
    private ICategoryHelper categoryHelper;
    @Autowired
    private IImageHelper imageHelper;

    private ImageInMemoryStreamer imageInMemoryStreamer = new ImageInMemoryStreamer();
    private ProductView productView;
    private Part uploadedFile;
    private boolean showSuccessMessage;
    private boolean isProductFound;

    private List<CategoryView> categories;
    private CategoryView emptyCategory = new CategoryView();

    private List<ImageView> newImages = new ArrayList<>();

    public void onPageLoad() {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) { return; }

        // If a product with passed in ID is not found -
        // it means we want to add a new product
        try {
            productView = productHelper.getProductViewFromNavigationQuery();
            isProductFound = true;
        } catch (IllegalArgumentException x) {
            productView = new ProductView();
            isProductFound = false;
        }

        categories = categoryHelper.findAll();
        //Omnifaces converter throws null pointer exception if I add an empty selectOneMenu item, so I have
        //to do it this way
        emptyCategory.setName("");
        categories.add(0, emptyCategory);

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

        if(newImages.size()==0){
            productView.getImages().add(imageHelper.getDefaultImage());
        }

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

    public void removeDiscount() {
        productView.setDiscount(null);
        productHelper.update(productView);
    }
}
