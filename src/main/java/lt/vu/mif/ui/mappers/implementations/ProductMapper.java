package lt.vu.mif.ui.mappers.implementations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lt.vu.mif.model.product.Category;
import lt.vu.mif.model.product.CategoryAttributeValue;
import lt.vu.mif.model.product.Product;
import lt.vu.mif.model.product.ProductAttributeValue;
import lt.vu.mif.repository.repository.implementations.AttributeValueRepository;
import lt.vu.mif.repository.repository.implementations.CategoryRepository;
import lt.vu.mif.ui.helpers.interfaces.IPriceResolver;
import lt.vu.mif.ui.mappers.interfaces.IMapper;
import lt.vu.mif.ui.view.AttributeValue;
import lt.vu.mif.ui.view.AttributeView;
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
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private AttributeValueRepository attributeValueRepository;

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
        product.setCategory(categoryRepository.get(view.getCategory().getId()));
        product.setImages(imageMapper.toEntities(view.getImages()));
        product.setCreationDate(LocalDateTime.now());
        product.setVersion(view.getVersion());

        if (product.getCategory() != null) {
            List<ProductAttributeValue> attributeValues = new ArrayList<>();

            //Climbs up category hierarchy to the top to save attributes
            CategoryView category = view.getCategory();
            while (category != null) {
                for (AttributeView attribute : category.getAttributes()) {
                    if (attribute.getSelectedValue() != null) {
                        CategoryAttributeValue attributeValue = attributeValueRepository
                            .getOne(attribute.getSelectedValue().getId());

                        ProductAttributeValue productAttributeValue = new ProductAttributeValue();
                        productAttributeValue.setCategoryAttributeValue(attributeValue);
                        productAttributeValue.setProduct(product);

                        attributeValues.add(productAttributeValue);
                    }
                }

                category = category.getParentCategory();
            }

            product.setAttributeValues(attributeValues);
        }

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
        view.setVersion(entity.getVersion());

        //Climbs up category hierarchy to the top to set attributes
        CategoryView category = view.getCategory();
        while (category != null) {
            for (AttributeView attributeView : category.getAttributes()) {
                for (ProductAttributeValue value : entity.getAttributeValues()) {
                    AttributeValue selected = searchValue(attributeView.getValues(),
                        value.getCategoryAttributeValue().getId());

                    // I don't know how this code exactly works, but this seems to fix it!!!
                    if (selected != null) {
                        attributeView.setSelectedValue(selected);
                        break;
                    }
                }
            }

            category = category.getParentCategory();
        }

        return view;
    }

    private AttributeValue searchValue(List<AttributeValue> attributeViews, Long id) {
        for (AttributeValue view : attributeViews) {
            if (view.getId().equals(id)) {
                return view;
            }
        }
        return null;
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
