package lt.vu.mif.repository.repository.interfaces;

import java.util.List;
import lt.vu.mif.model.product.Cart;

public interface ICartRepository {

    List<Cart> findAll();

    <S extends Cart> S save(S entity);

    Cart get(Long id);

    Cart update(Cart entity);

    void updateAll(List<Cart> carts);

    Cart getByUserId(Long userId);
}
