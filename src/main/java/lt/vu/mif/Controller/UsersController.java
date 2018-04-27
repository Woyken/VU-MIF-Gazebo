package lt.vu.mif.Controller;

import javax.faces.context.FacesContext;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.Repository.UserRepository;
import lt.vu.mif.Utils.Paging;
import lt.vu.mif.View.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

@Getter
@Setter
@Named
public class UsersController {
    @Autowired
    private UserRepository userRepository;

    private Page<UserView> usersPage;
    private Paging paging = new Paging();

    public void onPageLoad() {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            return;
        }

        paging.reset();
        searchUsers();
    }

    public void blockUser(UserView userView) {
        userRepository.blockUser(userView.getId(), !userView.isBlocked());
        userView.setBlocked(!userView.isBlocked());
    }

    public void next() {
        paging.next();
        searchUsers();
    }

    public void previous() {
        paging.previous();
        searchUsers();
    }

    public int getPagingIndex() {
        return paging.getIndex();
    }

    public void searchUsers(int activePage) {
        paging.setActivePage(activePage);
        searchUsers();
    }

    private void searchUsers() {
        usersPage = userRepository.getUsersPage(paging).map(UserView::new);
        paging.setTotalPages(usersPage.getTotalPages());
    }
}