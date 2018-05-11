package lt.vu.mif.ui.controller;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.ui.helpers.implementations.ProductHelper;
import lt.vu.mif.ui.helpers.interfaces.IUserHelper;
import lt.vu.mif.ui.paging.Paging;
import lt.vu.mif.ui.view.BoughtProductView;
import lt.vu.mif.ui.view.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.context.annotation.RequestScope;

@Named
@Getter
@Setter
@RequestScope
public class UserDetailsController {

    @Autowired
    private ProductHelper productHelper;
    @Autowired
    private IUserHelper userHelper;

    private Page<BoughtProductView> page;
    private UserView user;
    private Paging paging = new Paging();

    public void onPageLoad() {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            return;
        }

        String userId = FacesContext.getCurrentInstance().getExternalContext()
            .getRequestParameterMap().get("userId");
        user = userHelper.get(Long.valueOf(userId));

        paging.reset();
        search();
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