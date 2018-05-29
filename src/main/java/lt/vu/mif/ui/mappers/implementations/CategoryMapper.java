package lt.vu.mif.ui.mappers.implementations;

import lt.vu.mif.model.product.Category;
import lt.vu.mif.model.product.Discount;
import lt.vu.mif.repository.repository.interfaces.ICategoryRepository;
import lt.vu.mif.ui.mappers.interfaces.IMapper;
import lt.vu.mif.ui.view.CategoryView;
import lt.vu.mif.ui.view.DiscountView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
        entity.setAttributes(view.getAttributes());
        entity.setDiscount(discountMapper.toEntity(view.getDiscount()));

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
        view.setAttributes(entity.getAttributes());
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


        return view;
    }

    @Override
    public List<CategoryView> toViews(List<Category> entities) {
        return entities.stream().map(this::toView).collect(Collectors.toList());
    }
}
