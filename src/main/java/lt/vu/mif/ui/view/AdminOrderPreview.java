package lt.vu.mif.ui.view;

import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;

@Named
@Getter
@Setter
public class AdminOrderPreview extends OrderPreview {

    private RatingView ratingView;
}