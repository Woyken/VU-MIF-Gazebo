package lt.vu.mif.generator;

import lt.vu.mif.model.product.Category;
import lt.vu.mif.repository.repository.interfaces.ICategoryRepository;
import lt.vu.mif.utils.constants.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataGenerator {

    @Autowired
    private ICategoryRepository categoryRepository;

    public void initializeDatabase() {
        insertRootCategory();
    }

    private void insertRootCategory() {
        if (categoryRepository.getCategoryByName(Constants.ROOT_CATEGORY_NAME) == null) {
            Category category = new Category();
            category.setName(Constants.ROOT_CATEGORY_NAME);
            categoryRepository.save(category);
        }
    }

}
