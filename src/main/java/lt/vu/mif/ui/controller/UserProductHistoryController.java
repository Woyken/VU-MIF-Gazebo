package lt.vu.mif.ui.controller;

import java.math.BigDecimal;
import javax.inject.Named;
import lombok.Getter;
import lt.vu.mif.ui.helpers.interfaces.IProductHelper;
import lt.vu.mif.ui.paging.Paging;
import lt.vu.mif.ui.view.BoughtProductView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

@Named
@Getter
public class UserProductHistoryController {
    @Autowired
    private IProductHelper productHelper;

    private Page<BoughtProductView> products;
    private BigDecimal totalSum;
    private Paging paging = new Paging();

    public void onPageLoad() {
        searchProducts();
    }

    private void searchProducts() {
        products = productHelper.getCurrentUserBoughtProductsPage(paging.getActivePage(), paging.getPageSize());
        totalSum = productHelper.getProductsSum(products.getContent());
    }

}