package lt.vu.mif.ui.view;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartView {

    private Long id;
    private List<CartItemView> items;
    private UserView user;
}
