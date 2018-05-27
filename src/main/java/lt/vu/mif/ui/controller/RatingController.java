package lt.vu.mif.ui.controller;

import java.util.ArrayList;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.authentication.UserService;
import lt.vu.mif.ui.helpers.interfaces.IRatingHelper;
import lt.vu.mif.ui.view.OrderView;
import lt.vu.mif.ui.view.RatingView;
import lt.vu.mif.utils.SessionManager;
import lt.vu.mif.utils.constants.Constants;
import org.springframework.beans.factory.annotation.Autowired;

@Named
@Getter
@Setter
@ViewScoped
public class RatingController {

    @Autowired
    private IRatingHelper ratingHelper;
    @Autowired
    private SessionManager sessionManager;
    @Autowired
    private UserService userService;

    private List<OrderView> orderViews = new ArrayList<>();
    private List<RatingView> ratingViews = new ArrayList<>();

    public void onPageLoad() {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            return;
        }

        Object sessionAttribute = sessionManager.getAttribute(Constants.SHOW_DIALOG_SESSION_PARAMETER);
        if ((sessionAttribute != null && !Boolean.valueOf(sessionAttribute.toString())) || !userService.isLoggedIn()) {
            return;
        }

        clearData();
        orderViews = ratingHelper.getCurrentUserOrdersToRate();
        prepareRatingViews();
    }

    private void clearData() {
        orderViews.clear();
        ratingViews.clear();
    }

    private void prepareRatingViews() {
        for (OrderView orderView : orderViews) {
            RatingView ratingView = new RatingView();
            ratingView.setOrderId(orderView.getId());
            ratingViews.add(ratingView);
        }
    }

    public void rateOrders() {
        ratingHelper.rateOrders(ratingViews);
        sessionManager.removeAttribute(Constants.SHOW_DIALOG_SESSION_PARAMETER);
    }
}