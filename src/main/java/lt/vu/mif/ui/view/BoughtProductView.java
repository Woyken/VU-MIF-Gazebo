package lt.vu.mif.ui.view;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoughtProductView {

    private ImageView image;
    private String date;
    private long quantity;
    private BigDecimal price;
    private String title;
    private Long productId;
    private Long imageId;
}