package lt.vu.mif.ui.helpers.interfaces;

import java.util.List;
import lt.vu.mif.ui.view.CategoryView;

public interface ICategoryHelper {
    List<CategoryView> findAll();

    CategoryView get(Long id);

    void save(CategoryView view);
}