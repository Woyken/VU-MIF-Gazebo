package lt.vu.mif.ui.helpers.interfaces;

import lt.vu.mif.ui.view.CategoryView;

import java.util.List;

public interface ICategoryHelper {

    List<CategoryView> findAll();

    CategoryView get(Long id);

    CategoryView getCategoryByName(String name);

    void save(CategoryView view);

    void update(CategoryView view);

    void delete(CategoryView view);
}
