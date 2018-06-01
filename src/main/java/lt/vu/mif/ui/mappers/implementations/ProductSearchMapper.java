package lt.vu.mif.ui.mappers.implementations;

import java.util.ArrayList;
import java.util.List;
import lt.vu.mif.model.product.Category;
import lt.vu.mif.model.product.CategoryAttributeValue;
import lt.vu.mif.model.product.ProductAttributeValue;
import lt.vu.mif.repository.repository.implementations.AttributeValueRepository;
import lt.vu.mif.ui.mappers.interfaces.IMapper;
import lt.vu.mif.ui.view.AttributeValue;
import lt.vu.mif.ui.view.CategoryView;
import lt.vu.mif.ui.view.ProductSearchView;
import lt.vu.mif.utils.search.ProductSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductSearchMapper implements IMapper<ProductSearch, ProductSearchView> {

    @Autowired
    IMapper<Category, CategoryView> categoryMapper;
    @Autowired
    private AttributeValueRepository attributeValueRepository;

    @Override
    public ProductSearch toEntity(ProductSearchView view) {
        if (view == null) {
            return null;
        }

        ProductSearch entity = new ProductSearch();
        entity.setTitle(view.getTitle());
        entity.setMinPrice(view.getMinPrice());
        entity.setMaxPrice(view.getMaxPrice());
        entity.setCategories(categoryMapper.toEntities(view.getCategories()));

        //Should be in a mapper, but gotta go fast =]]]]
        if (view.getAttributeValues() != null) {
            for (AttributeValue v : view.getAttributeValues()) {
                CategoryAttributeValue attributeValue = attributeValueRepository
                    .getOne(v.getId());

                ProductAttributeValue productAttributeValue = new ProductAttributeValue();
                productAttributeValue.setCategoryAttributeValue(attributeValue);

                entity.getSelectedAttributeValues().add(v.getId());
            }
        }


        entity.setIncludeDeleted(view.isIncludeDeleted());
        entity.setSortBy(view.getSortBy());

        return entity;
    }

    @Override
    public List<ProductSearch> toEntities(List<ProductSearchView> views) {
        return null;
    }

    @Override
    public ProductSearchView toView(ProductSearch entity) {
        return null;
    }

    @Override
    public List<ProductSearchView> toViews(List<ProductSearch> entities) {
        return null;
    }
}
