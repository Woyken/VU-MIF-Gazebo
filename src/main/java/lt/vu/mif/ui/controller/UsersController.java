package lt.vu.mif.ui.controller;

import java.util.ArrayList;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.ui.helpers.interfaces.IUserHelper;
import lt.vu.mif.ui.view.AdminUserView;
import lt.vu.mif.ui.view.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.RequestScope;

@Getter
@Setter
@Named
@ViewScoped
public class UsersController {
    @Autowired
    private IUserHelper userHelper;

    private List<AdminUserView> users = new ArrayList<>();

    public void onPageLoad() {
        users = userHelper.getAllUsers();
    }

    public void blockUser(UserView userView) {
        userHelper.blockUser(userView.getId(), !userView.isBlocked());
    }
}