package lt.vu.mif.ui.mappers.implementations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lt.vu.mif.model.product.Category;
import lt.vu.mif.model.product.Product;
import lt.vu.mif.ui.helpers.interfaces.IPriceResolver;
import lt.vu.mif.ui.mappers.interfaces.IMapper;
import lt.vu.mif.ui.view.CategoryView;
import lt.vu.mif.ui.view.ProductView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component("productMapper")
public class ProductMapper implements IMapper<Product, ProductView> {

    @Autowired
    private ImageMapper imageMapper;
    @Autowired
    private DiscountMapper discountMapper;
    @Autowired
    private IMapper<Category, CategoryView> categoryMapper;
    @Autowired
    private IPriceResolver priceResolver;

    public Product toEntity(ProductView view) {
        if (view == null) {
            return null;
        }

        Product product = new Product();

        product.setId(view.getId());
        product.setDescription(view.getDescription());
        product.setPrice(view.getPrice());
        product.setSku(view.getSku());
        product.setTitle(view.getTitle());
        product.setDiscount(discountMapper.toEntity(view.getDiscount()));
        product.setCategory(categoryMapper.toEntity(view.getCategory()));
        product.setImages(imageMapper.toEntities(view.getImages()));
        product.setCreationDate(LocalDateTime.now());

        return product;
    }

    public ProductView toView(Product entity) {
        if (entity == null) {
            return null;
        }

        ProductView view = new ProductView();

        view.setId(entity.getId());
        view.setDescription(entity.getDescription());
        view.setPrice(entity.getPrice());
        view.setSku(entity.getSku());
        view.setTitle(entity.getTitle());
        view.setDiscount(discountMapper.toView(entity.getDiscount()));
        view.setCategory(categoryMapper.toView(entity.getCategory()));
        view.setImages(imageMapper.toViews(entity.getImages()));
        view.setNewPrice(priceResolver.resolvePriceWithDiscount(entity));

        return view;
    }


    @Override
    public List<Product> toEntities(List<ProductView> views) {
        if (CollectionUtils.isEmpty(views)) {
            return Collections.emptyList();
        }

        return views.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public List<ProductView> toViews(List<Product> entities) {
        if (CollectionUtils.isEmpty(entities)) {
            return Collections.emptyList();
        }

        return entities.stream().map(this::toView).collect(Collectors.toList());
    }
}
