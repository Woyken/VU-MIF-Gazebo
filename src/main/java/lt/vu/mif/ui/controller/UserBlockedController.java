package lt.vu.mif.ui.controller;

import java.io.IOException;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import lt.vu.mif.authentication.UserService;
import lt.vu.mif.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;

@Named
public class UserBlockedController {

    @Autowired
    private UserService userService;

    public void onPageLoad() throws IOException {
        User user = userService.getLoggedUser();

        if (user != null && user.isBlocked()) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("blocked.xhtml");
        }
    }
}
