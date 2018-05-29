package lt.vu.mif.ui.view;

import lombok.Getter;
import lombok.Setter;

import javax.inject.Named;

@Named
@Getter
@Setter
public class AdminOrderPreview extends OrderPreview {

    private RatingView ratingView;
}