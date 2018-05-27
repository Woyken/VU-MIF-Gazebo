package lt.vu.mif.ui.controller;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.model.order.OrderStatus;
import lt.vu.mif.ui.helpers.interfaces.IOrdersHelper;
import lt.vu.mif.ui.view.AdminOrderPreview;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
@Named
@ViewScoped
public class OrderDetailsController {

    @Autowired
    private IOrdersHelper ordersHelper;

    private AdminOrderPreview orderView;

    public void onPageLoad() {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            return;
        }

        String orderId = FacesContext.getCurrentInstance().getExternalContext()
            .getRequestParameterMap().get("orderId");
        if (StringUtils.isBlank(orderId)) {
            throw new IllegalArgumentException("Invalid request parameter");
        }

        orderView = ordersHelper.getAdminOrder(Long.valueOf(orderId));

        if (orderView == null) {
            throw new IllegalStateException("Order" + "with ID=" + orderId + "not found");
        }
    }

    public void updateOrderStatus(String status) {
        ordersHelper.setOrderStatus(orderView.getId(), OrderStatus.valueOf(status));
        orderView = ordersHelper.getAdminOrder(orderView.getId());
    }
}
