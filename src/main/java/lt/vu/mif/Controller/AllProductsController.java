package lt.vu.mif.Controller;

import java.util.List;
import java.util.stream.Collectors;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.Repository.ProductRepository;
import lt.vu.mif.View.ProductView;
import org.springframework.beans.factory.annotation.Autowired;

@Named
@ViewScoped
public class AllProductsController {

    @Autowired
    private ProductRepository productRepository;

    @Getter
    private List<ProductView> products;

    @Setter
    private Long productToDeleteId;


    public void onPageLoad() {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            return;
        }

        getProducts();
    }

    private void getProducts() {
        products = productRepository.getAll().stream().map(ProductView::new)
            .collect(Collectors.toList());
    }

    public String deleteProduct() {
        if (productToDeleteId == null) {
            throw new IllegalStateException("productToDelete was not set");
        }

        productRepository.deleteById(productToDeleteId);

        getProducts();

        return "admin-all-products?faces-redirect=true";
    }
}
