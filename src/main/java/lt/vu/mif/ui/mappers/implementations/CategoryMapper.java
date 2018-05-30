package lt.vu.mif.ui.mappers.implementations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lt.vu.mif.model.product.CategoryAttributeValue;
import lt.vu.mif.model.product.Category;
import lt.vu.mif.model.product.CategoryAttribute;
import lt.vu.mif.model.product.Discount;
import lt.vu.mif.repository.repository.interfaces.ICategoryRepository;
import lt.vu.mif.ui.mappers.interfaces.IMapper;
import lt.vu.mif.ui.view.AttributeValue;
import lt.vu.mif.ui.view.AttributeView;
import lt.vu.mif.ui.view.CategoryView;
import lt.vu.mif.ui.view.DiscountView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class CategoryMapper implements IMapper<Category, CategoryView> {

    @Autowired
    private IMapper<Discount, DiscountView> discountMapper;
    @Autowired
    private ICategoryRepository categoryRepository;

    @Override
    public Category toEntity(CategoryView view) {
        if (view == null) {
            return null;
        }

        Category entity = new Category();

        entity.setId(view.getId());
        if (view.getParentCategory() != null) {
            entity.setParentCategory(toEntity(view.getParentCategory()));
        }
        entity.setName(view.getName());
        entity.setDiscount(discountMapper.toEntity(view.getDiscount()));

        List<AttributeView> attributes = view.getAttributes();

        if (attributes != null) {
            List<CategoryAttribute> categoryAttributes = new ArrayList<>();

            for (AttributeView attributeView : attributes) {
                CategoryAttribute categoryAttribute = new CategoryAttribute();
                categoryAttribute.setName(attributeView.getName());
                categoryAttribute.setCategory(entity);

                for (AttributeValue value : attributeView.getValues()) {
                    CategoryAttributeValue attributeValue = new CategoryAttributeValue();
                    attributeValue.setValue(value.getValue());
                    attributeValue.setId(value.getId());
                    attributeValue.setCategoryAttribute(categoryAttribute);

                    categoryAttribute.getValues().add(attributeValue);
                }

                categoryAttributes.add(categoryAttribute);
            }

            entity.setAttributes(categoryAttributes);
        }

        return entity;
    }

    @Override
    public List<Category> toEntities(List<CategoryView> views) {
        if (CollectionUtils.isEmpty(views)) {
            return Collections.emptyList();
        }

        return views.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public CategoryView toView(Category entity) {
        if (entity == null) {
            return null;
        }

        CategoryView view = new CategoryView();

        view.setId(entity.getId());
        view.setName(entity.getName());
        view.setParentCategory(toView(entity.getParentCategory()));
//        view.setAttributes(entity.getAttributes());
        view.setDiscount(discountMapper.toView(entity.getDiscount()));

        //Only root category will not have a parent
        if (view.getParentCategory() != null) {
            view.setNameWithParents(
                view.getParentCategory().getNameWithParents() + "/" + view.getName());
        } else {
            //Name with parents can't be null, because in category page drop down menu when chosen
            //controller would get null instead of this category
            view.setNameWithParents("/");
        }

        List<CategoryAttribute> attributeViews = entity.getAttributes();
        List<AttributeView> views = new ArrayList<>();


        for (CategoryAttribute categoryAttribute : attributeViews) {
            AttributeView attributeView = new AttributeView();
            attributeView.setName(categoryAttribute.getName());
            attributeView.setId(categoryAttribute.getId());

            for (CategoryAttributeValue value : categoryAttribute.getValues()) {
                AttributeValue attributeValue = new AttributeValue();
                attributeValue.setId(value.getId());
                attributeValue.setValue(value.getValue());

                attributeView.getValues().add(attributeValue);
            }

            views.add(attributeView);
        }

        view.setAttributes(views);

        return view;
    }

    @Override
    public List<CategoryView> toViews(List<Category> entities) {
        return entities.stream().map(this::toView).collect(Collectors.toList());
    }
}
