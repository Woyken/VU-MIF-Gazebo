package lt.vu.mif.ui.controller;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import lt.vu.mif.ui.helpers.interfaces.IUserHelper;
import lt.vu.mif.ui.view.UserView;
import org.springframework.beans.factory.annotation.Autowired;

@Named
@ViewScoped
public class AuthenticationController {

    @Autowired
    private IUserHelper userHelper;

    private UserView loggedInUser;

    public UserView getLoggedInUser() {
        if (null != loggedInUser) {
            return loggedInUser;
        }
        return loggedInUser = userHelper.getLoggedInUser();
    }
    public boolean isLoggedIn() {
        return userHelper.isLoggedIn();
    }
}
