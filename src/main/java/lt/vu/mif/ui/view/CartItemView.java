package lt.vu.mif.ui.view;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemView extends ProductView {

    private Long amount;
    private Long cartItemId;
    private CartView cart;

    public CartItemView(ProductView product) {
        super(product);
    }
}
