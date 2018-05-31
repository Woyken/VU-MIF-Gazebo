package lt.vu.mif.generator.realizations;

import lt.vu.mif.generator.interfaces.IDataGenerator;
import lt.vu.mif.model.product.Category;
import lt.vu.mif.repository.repository.interfaces.ICategoryRepository;
import lt.vu.mif.utils.constants.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataGenerator implements IDataGenerator {

    @Autowired
    private CategoryInserter categoryInserter;

    @Override
    public void generateData() {
        categoryInserter.insertRootCategory();
    }
}
