package lt.vu.mif.ui.helpers.implementations;

import java.util.List;
import lt.vu.mif.model.product.Category;
import lt.vu.mif.model.product.Product;
import lt.vu.mif.repository.repository.interfaces.ICategoryRepository;
import lt.vu.mif.repository.repository.interfaces.IProductRepository;
import lt.vu.mif.ui.helpers.interfaces.ICategoryHelper;
import lt.vu.mif.ui.mappers.interfaces.IMapper;
import lt.vu.mif.ui.view.CategoryView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class CategoryHelper implements ICategoryHelper {

    @Autowired
    private ICategoryRepository categoryRepository;
    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private IMapper<Category, CategoryView> categoryMapper;

    @Override
    public List<CategoryView> findAll() {
        return categoryMapper.toViews(categoryRepository.findAll());
    }

    @Override
    public CategoryView get(Long id) {
        return categoryMapper.toView(categoryRepository.get(id));
    }

    @Override
    public CategoryView getCategoryByName(String name) {
        return categoryMapper.toView(categoryRepository.getCategoryByName(name));
    }

    @Override
    public void save(CategoryView view) {
        Category entity = categoryMapper.toEntity(view);
        categoryRepository.save(entity);
    }

    @Override
    public void update(CategoryView view) {
        Category entity = categoryMapper.toEntity(view);
        categoryRepository.update(entity);
    }

    @Override
    public void delete(CategoryView view) {
        Category entity = categoryMapper.toEntity(view);
        Category root = categoryRepository.getRootCategory();

        List<Product> products = productRepository.getAllByCategory(entity);
        for (Product p : products) {
            p.setCategory(root);
        }
        productRepository.saveAll(entity.getProducts());

        categoryRepository.delete(categoryRepository.update(entity));
    }
}