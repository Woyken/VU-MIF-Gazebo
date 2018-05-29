package lt.vu.mif.ui.mappers.implementations;

import lt.vu.mif.model.product.CartItem;
import lt.vu.mif.ui.mappers.interfaces.IMapper;
import lt.vu.mif.ui.view.CartItemView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component("cartItemMapper")
public class CartItemMapper implements IMapper<CartItem, CartItemView> {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public CartItem toEntity(CartItemView view) {
        if (null == view) {
            return null;
        }
        CartItem entity = new CartItem();
        entity.setAmount(view.getAmount());
        entity.setId(view.getCartItemId());
        entity.setProduct(productMapper.toEntity(view));

        return entity;
    }

    @Override
    public List<CartItem> toEntities(List<CartItemView> views) {
        if (CollectionUtils.isEmpty(views)) {
            return Collections.emptyList();
        }

        return views.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public CartItemView toView(CartItem entity) {
        if (entity == null) {
            return null;
        }

        CartItemView view = new CartItemView(productMapper.toView(entity.getProduct()));
        view.setAmount(entity.getAmount());
        view.setCartItemId(entity.getId());

        return view;
    }

    @Override
    public List<CartItemView> toViews(List<CartItem> entities) {
        if (CollectionUtils.isEmpty(entities)) {
            return Collections.emptyList();
        }

        return entities.stream().map(this::toView).collect(Collectors.toList());
    }
}
