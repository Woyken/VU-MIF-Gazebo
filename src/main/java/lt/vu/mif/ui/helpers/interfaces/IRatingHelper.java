package lt.vu.mif.ui.helpers.interfaces;

import lt.vu.mif.ui.view.OrderView;
import lt.vu.mif.ui.view.RatingView;

import java.util.List;

public interface IRatingHelper {

    List<OrderView> getCurrentUserOrdersToRate();

    void rateOrders(List<RatingView> ratingViews);
}