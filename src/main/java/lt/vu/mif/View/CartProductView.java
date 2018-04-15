package lt.vu.mif.View;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartProductView extends ProductView {
    private Long amount;

    public CartProductView(ProductView product) {
        super(product);
        this.amount = 0L;
    }

}
