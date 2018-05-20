package lt.vu.mif.ui.mappers.implementations;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lt.vu.mif.model.product.Category;
import lt.vu.mif.repository.repository.interfaces.ICategoryRepository;
import lt.vu.mif.ui.mappers.interfaces.IMapper;
import lt.vu.mif.ui.view.CategoryView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class CategoryMapper implements IMapper<Category, CategoryView> {

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
            entity.setParentCategory(categoryRepository.get(view.getParentCategory().getId()));
        }
        entity.setName(view.getName());
        entity.setAttributes(view.getAttributes());

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

        return view;
    }

    @Override
    public List<CategoryView> toViews(List<Category> entities) {
        return entities.stream().map(this::toView).collect(Collectors.toList());
    }
}
