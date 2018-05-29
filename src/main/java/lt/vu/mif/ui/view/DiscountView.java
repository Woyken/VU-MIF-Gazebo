package lt.vu.mif.ui.view;

import lombok.Getter;
import lombok.Setter;

import javax.inject.Named;
import java.math.BigDecimal;

@Named
@Getter
@Setter
public class DiscountView {

    private Long id;
    private Long percentageDiscount;
    private BigDecimal absoluteDiscount;
    private String startDate;
    private String startTime;
    private String endDate;
    private String endTime;

    public DiscountView() {
    }

    public DiscountView(DiscountView other) {
        this.id = other.id;
        this.percentageDiscount = other.percentageDiscount;
        this.absoluteDiscount = other.absoluteDiscount;
        this.startDate = other.startDate;
        this.startTime = other.startTime;
        this.endDate = other.endDate;
        this.endTime = other.endTime;
    }
}
