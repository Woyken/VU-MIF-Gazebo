package lt.vu.mif.ui.view;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BoughtProductView {

    private Long id;
    private ImageView image;
    private String date;
    private long quantity;
    private BigDecimal price;
    private String title;
    private Long productId;
    private Long imageId;
}