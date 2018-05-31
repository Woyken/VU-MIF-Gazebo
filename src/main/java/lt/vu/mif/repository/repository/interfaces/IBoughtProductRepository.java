package lt.vu.mif.repository.repository.interfaces;

import lt.vu.mif.model.order.OrderProduct;
import org.springframework.data.domain.Page;

public interface IBoughtProductRepository {

    Page<OrderProduct> getBoughtProductsPage(int activePage, int pageSize, Long userId);
}
