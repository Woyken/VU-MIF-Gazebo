package lt.vu.mif.Controller;

import javax.faces.context.FacesContext;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.Entity.User;
import lt.vu.mif.Repository.BoughtProductRepository;
import lt.vu.mif.Repository.UserRepository;
import lt.vu.mif.Utils.Paging;
import lt.vu.mif.View.BoughtProductView;
import lt.vu.mif.View.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

@Named
@Getter
@Setter
public class UserDetailsController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BoughtProductRepository boughtProductRepository;

    private Page<BoughtProductView> page;
    private UserView user;
    private Paging paging = new Paging();

    public void onPageLoad() {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            return;
        }

        String userId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("userId");
        user = new UserView(userRepository.get(User.class, Long.valueOf(userId)));

        paging.reset();
        search();
    }

    private void search() {
        page = boughtProductRepository.getBoughtProductsPage(paging, user.getId())
            .map(BoughtProductView::new);
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