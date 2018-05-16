package lt.vu.mif.ui.controller;

import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.ui.helpers.interfaces.IOrdersHelper;
import lt.vu.mif.ui.view.OrderView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Getter
@Setter
@Named
@ViewScoped
public class OrderDetailsController {
    @Autowired
    private IOrdersHelper ordersHelper;

    private OrderView orderView;

    public void onPageLoad() {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            return;
        }

        String orderId = FacesContext.getCurrentInstance().getExternalContext()
                .getRequestParameterMap().get("orderId");
        if (StringUtils.isBlank(orderId)) {
            throw new IllegalArgumentException("Invalid request parameter");
        }

        orderView = ordersHelper.getOrder(Long.valueOf(orderId));

        if (orderView == null) {
            throw new IllegalStateException("Order" + "with ID=" + orderId + "not found");
        }
    }

    public void saveChanges() {
    }
}
