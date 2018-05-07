package lt.vu.mif.ui.controller;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.ui.helpers.interfaces.IUserHelper;
import lt.vu.mif.ui.view.UserView;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
@Named
public class UsersController {
    @Autowired
    private IUserHelper userHelper;

    private List<UserView> users = new ArrayList<>();

    public void onPageLoad() {
        users = userHelper.getAllUsers();
    }

    public void blockUser(UserView userView) {
        userHelper.blockUser(userView.getId(), !userView.isBlocked());
    }
}