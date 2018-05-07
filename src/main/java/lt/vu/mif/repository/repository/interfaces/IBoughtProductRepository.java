package lt.vu.mif.repository.repository.interfaces;

import org.springframework.data.domain.Page;

import lt.vu.mif.model.order.OrderProduct;

public interface IBoughtProductRepository {
    Page<OrderProduct> getBoughtProductsPage(int activePage, int pageSize, Long userId);
}
