package lt.vu.mif.ui.view;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;

@Named
@Getter
@Setter
public class DiscountView {

    private Long id;
    private Long percentageDiscount;
    private BigDecimal absoluteDiscount;
    private LocalDateTime from;
    private LocalDateTime to;

    public DiscountView() {
    }

    public DiscountView(DiscountView other) {
        this.id = other.id;
        this.percentageDiscount = other.percentageDiscount;
        this.absoluteDiscount = other.absoluteDiscount;
        this.from = other.from;
        this.to = other.to;
    }
}
