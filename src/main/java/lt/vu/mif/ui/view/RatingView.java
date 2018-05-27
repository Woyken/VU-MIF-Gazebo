package lt.vu.mif.ui.view;

import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;

@Named
@Getter
@Setter
public class RatingView {
    private Long orderId;
    private Long value;
    private String comment;
    private String date;
}
