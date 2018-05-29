package lt.vu.mif.ui.controller;

import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.Logging.Logged;
import lt.vu.mif.ui.helpers.implementations.ProductHelper;
import lt.vu.mif.ui.helpers.interfaces.IUserHelper;
import lt.vu.mif.ui.paging.Paging;
import lt.vu.mif.ui.view.AdminUserView;
import lt.vu.mif.ui.view.BoughtProductView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Logged
@Named
@Getter
@Setter
@ViewScoped
public class UserDetailsController {

    @Autowired
    private ProductHelper productHelper;
    @Autowired
    private IUserHelper userHelper;

    private Page<BoughtProductView> page;
    private AdminUserView user;
    private Paging paging = new Paging();

    public void onPageLoad() {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            return;
        }

        String userId = FacesContext.getCurrentInstance().getExternalContext()
                .getRequestParameterMap().get("userId");
        user = userHelper.getAdminView(Long.valueOf(userId));

        paging.reset();
        search();
    }

    public void blockUser() {
        userHelper.blockUser(user.getId(), !user.isBlocked());
        user = userHelper.getAdminView(user.getId());
    }

    private void search() {
        page = productHelper
                .getBoughtProductsPage(paging.getActivePage(), paging.getPageSize(), user.getId());
        paging.setTotalPages(page.getTotalPages());
    }

    public void next() {
        paging.next();
        search();
    }

    public void previous() {
        paging.previous();
        search();
    }

    public long getPagingIndex() {
        return paging.getIndex();
    }

    public void search(int activePage) {
        paging.setActivePage(activePage);
        search();
    }

}