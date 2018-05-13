package lt.vu.mif.repository.repository.interfaces;

import lt.vu.mif.model.order.OrderRating;

public interface IOrderRatingRepository {

    OrderRating save (OrderRating rating);

    OrderRating get(Long id);
}
