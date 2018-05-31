package lt.vu.mif.ui.view;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.model.order.OrderStatus;

@Named
@Getter
@Setter
public class OrderPreview {

    private Long id;
    private Long userId;
    private String userEmail;
    private OrderStatus status;
    private String creationDate;
    private BigDecimal totalSum;
    private List<BoughtProductView> products = new ArrayList<>();
}
