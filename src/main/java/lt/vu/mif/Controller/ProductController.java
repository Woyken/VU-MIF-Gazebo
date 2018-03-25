package lt.vu.mif.Controller;

import lombok.Getter;
import lt.vu.mif.Repository.ProductRepository;
import lt.vu.mif.View.ProductView;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;
import java.util.List;
import java.util.stream.Collectors;

@Named
@Getter
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    private List<ProductView> products;

    public void onPageLoad() {
        products = productRepository.getAll().stream().map(ProductView::new)
            .collect(Collectors.toList());
    }
}