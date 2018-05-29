package lt.vu.mif.ui.view;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AdminUserView extends UserView {

    private long ordersCount;
    private BigDecimal averagePrice;
    private BigDecimal ordersSum;
}