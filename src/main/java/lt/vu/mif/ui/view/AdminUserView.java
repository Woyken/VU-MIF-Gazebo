package lt.vu.mif.ui.view;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminUserView extends UserView {
    private long ordersCount;
    private BigDecimal averagePrice;
    private BigDecimal ordersSum;
}