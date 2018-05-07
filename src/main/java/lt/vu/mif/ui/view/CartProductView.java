package lt.vu.mif.ui.view;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartProductView extends ProductView {
    private long amount;

    public CartProductView(ProductView product) {
        super(product);
    }

}