package lt.vu.mif.repository.repository.interfaces;

import java.util.List;
import lt.vu.mif.model.product.Category;

public interface ICategoryRepository {
    List<Category> findAll();

    <S extends Category> S save(S entity);
}