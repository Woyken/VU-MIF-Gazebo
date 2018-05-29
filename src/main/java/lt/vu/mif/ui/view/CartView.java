package lt.vu.mif.ui.view;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartView {

    private Long id;
    private List<CartItemView> items;
    private UserView user;
}
