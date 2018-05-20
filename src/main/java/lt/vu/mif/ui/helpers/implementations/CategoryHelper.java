package lt.vu.mif.ui.helpers.implementations;

import java.util.List;
import lt.vu.mif.model.product.Category;
import lt.vu.mif.repository.repository.interfaces.ICategoryRepository;
import lt.vu.mif.ui.helpers.interfaces.ICategoryHelper;
import lt.vu.mif.ui.mappers.implementations.CategoryMapper;
import lt.vu.mif.ui.view.CategoryView;
import org.springframework.beans.factory.annotation.Autowired;

public class CategoryHelper implements ICategoryHelper {

    @Autowired
    private ICategoryRepository categoryRepository;
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<CategoryView> findAll() {
        return categoryMapper.toViews(categoryRepository.findAll());
    }

    @Override
    public CategoryView get(Long id) {
        return categoryMapper.toView(categoryRepository.get(id));
    }

    @Override
    public void save(CategoryView view) {
        Category entity = categoryMapper.toEntity(view);
        categoryRepository.save(entity);
    }
}