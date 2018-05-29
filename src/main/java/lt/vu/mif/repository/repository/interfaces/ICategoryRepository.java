package lt.vu.mif.repository.repository.interfaces;

import lt.vu.mif.model.product.Category;

import java.util.List;

public interface ICategoryRepository {

    List<Category> findAll();

    <S extends Category> S save(S entity);

    Category get(Long id);

    Category getCategoryByName(String name);

    Category getRootCategory();

    Category update(Category entity);

    void updateAll(List<Category> products);

    void delete(Category entity);
}
