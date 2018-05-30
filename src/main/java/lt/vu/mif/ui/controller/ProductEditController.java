package lt.vu.mif.ui.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.Part;
import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.Logging.Logged;
import lt.vu.mif.repository.repository.implementations.ProductRepository;
import lt.vu.mif.repository.repository.interfaces.IProductRepository;
import lt.vu.mif.ui.helpers.interfaces.ICategoryHelper;
import lt.vu.mif.ui.helpers.interfaces.IImageHelper;
import lt.vu.mif.ui.helpers.interfaces.IProductHelper;
import lt.vu.mif.ui.validation.EditProductValidation;
import lt.vu.mif.ui.view.CategoryView;
import lt.vu.mif.ui.view.DiscountView;
import lt.vu.mif.ui.view.ImageInMemoryStreamer;
import lt.vu.mif.ui.view.ImageView;
import lt.vu.mif.ui.view.ProductView;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;

@Logged
@Getter
@ViewScoped
@Named
public class ProductEditController implements Validator {

    @Autowired
    private IProductHelper productHelper;
    @Autowired
    private ICategoryHelper categoryHelper;
    @Autowired
    private IImageHelper imageHelper;
    @Autowired
    private ImageInMemoryStreamer imageInMemoryStreamer;
    @Autowired
    private EditProductValidation editProductValidation;

    private ProductView productView;
    private DiscountView discountView;

    @Setter
    private Part uploadedFile;
    @Setter
    private boolean discount;

    private boolean showSuccessMessage;
    private boolean isProductFound;

    private List<CategoryView> categories;

    private List<ImageView> newImages = new ArrayList<>();
    private ProductView conflictingProductView;

    public void onPageLoad() {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            return;
        }

        // If a product with passed in ID is not found -
        // it means we want to add a new product
        try {
            productView = productHelper.getProductViewFromNavigationQuery();
            discountView = productView.getDiscount() == null ? new DiscountView() : productView.getDiscount();
            conflictingProductView = null;
            isProductFound = true;
        } catch (IllegalArgumentException x) {
            productView = new ProductView();
            isProductFound = false;
        }

        showSuccessMessage = false;
        categories = categoryHelper.findAll();
        discount = productView.getDiscount() != null;
        Collections.sort(categories);
    }

    public void handleUploadedFile() throws Exception {
        ImageView imageView = new ImageView();
        imageView.setId(ThreadLocalRandom.current().nextLong(Long.MIN_VALUE, 0));
        imageView.setBytes(IOUtils.toByteArray(uploadedFile.getInputStream()));

        imageInMemoryStreamer.addImage(imageView.getId(), uploadedFile.getInputStream());
        newImages.add(imageView);
    }

    public void saveChanges() {
        productView.setDiscount(discount ? discountView : null);

        //Remove discount if it is smaller than new price
        if (productView.getDiscount() != null &&
            productView.getDiscount().getAbsoluteDiscount() != null &&
            productView.getPrice().compareTo(productView.getDiscount().getAbsoluteDiscount())
                == -1) {
            productView.setDiscount(null);
        }

        productView.getImages().addAll(newImages);

        if (newImages.isEmpty() && productView.getImages().isEmpty()) {
            productView.getImages().add(imageHelper.getDefaultImage());
        }

        try {
            productHelper.update(productView);
            productView.setVersion(productHelper.getProductVersion(productView.getId()));
            showSuccessMessage = true;
            clearData();
        } catch (OptimisticLockingFailureException ex) {
            ex.printStackTrace();
            conflictingProductView = productHelper.getProduct(productView.getId());
        }
    }

    private void clearData() {
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

    public void updateProductView() {
        productView = productHelper.getProduct(productView.getId());
        conflictingProductView = null;
    }

    public void overrideProductView() {
        productView.setVersion(conflictingProductView.getVersion());
        saveChanges();
        conflictingProductView = null;
    }

    public void check(boolean selected) {
        this.discount = !selected;
    }

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        String sku = (String) ((UIInput) uiComponent.getAttributes().get("sku")).getValue();

        if(isProductFound) {
            if (!productHelper.getProduct(productView.getId()).getSku().equals(sku)) {
                if (productHelper.checkIfProductExists(sku)) {
                    throw new ValidatorException(
                            new FacesMessage("Nurodytas SKU kodas jau egzistuoja sisemoje. Pateikite unikalų SKU kodą"));
                }
            }
        }
        else {
            if (productHelper.checkIfProductExists(sku)) {
                throw new ValidatorException(
                        new FacesMessage("Nurodytas SKU kodas jau egzistuoja sisemoje. Pateikite unikalų SKU kodą"));
            }
        }
    }
}
