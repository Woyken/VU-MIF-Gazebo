package lt.vu.mif.ui.view;

import lombok.Getter;
import lombok.Setter;

import javax.inject.Named;

@Named
@Getter
@Setter
public class RatingView {

    private Long orderId;
    private Long value;
    private String comment;
    private String date;
}
