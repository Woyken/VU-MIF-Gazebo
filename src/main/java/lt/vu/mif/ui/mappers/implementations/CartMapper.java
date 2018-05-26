package lt.vu.mif.ui.mappers.implementations;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lt.vu.mif.model.product.Cart;
import lt.vu.mif.model.product.CartItem;
import lt.vu.mif.ui.mappers.interfaces.IMapper;
import lt.vu.mif.ui.view.CartItemView;
import lt.vu.mif.ui.view.CartView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component("cartMapper")
public class CartMapper implements IMapper<Cart, CartView> {

    @Autowired
    private CartItemMapper cartItemMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public Cart toEntity(CartView view) {
        if (null == view) {
            return null;
        }

        Cart entity = new Cart();
        entity.setUser(userMapper.toEntity(view.getUser()));
        entity.setId(view.getId());
        List<CartItem> cartItems = cartItemMapper.toEntities(view.getItems());
        cartItems.forEach((CartItem item) -> item.setCart(entity));
        entity.setItems(cartItems);

        return entity;
    }

    @Override
    public List<Cart> toEntities(List<CartView> views) {
        if (CollectionUtils.isEmpty(views)) {
            return Collections.emptyList();
        }

        return views.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public CartView toView(Cart entity) {
        if (entity == null) {
            return null;
        }

        CartView view = new CartView();
        view.setUser(userMapper.toView(entity.getUser()));
        view.setId(entity.getId());
        List<CartItemView> items = cartItemMapper.toViews(entity.getItems());
        items.forEach((CartItemView itemView) -> itemView.setCart(view));
        view.setItems(items);

        return view;
    }

    @Override
    public List<CartView> toViews(List<Cart> entities) {
        if (CollectionUtils.isEmpty(entities)) {
            return Collections.emptyList();
        }

        return entities.stream().map(this::toView).collect(Collectors.toList());
    }
}
