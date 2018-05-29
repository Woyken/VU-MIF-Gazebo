package lt.vu.mif.repository.repository.interfaces;

import lt.vu.mif.model.product.Cart;

import java.util.List;

public interface ICartRepository {

    List<Cart> findAll();

    <S extends Cart> S save(S entity);

    Cart get(Long id);

    Cart update(Cart entity);

    void updateAll(List<Cart> carts);

    Cart getByUserId(Long userId);
}
