package lt.vu.mif.ui.helpers.interfaces;

import java.util.List;
import lt.vu.mif.ui.view.OrderView;
import lt.vu.mif.ui.view.RatingView;

public interface IRatingHelper {

    List<OrderView> getCurrentUserOrdersToRate();

    void rateOrders(List<RatingView> ratingViews);
}