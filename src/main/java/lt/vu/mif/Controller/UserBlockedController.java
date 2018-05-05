package lt.vu.mif.Controller;

import java.io.IOException;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import lt.vu.mif.Entity.User;
import lt.vu.mif.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

@Named
public class UserBlockedController {

    @Autowired
    private UserService userService;

    public void onPageLoad() throws IOException {
        User user = userService.getLoggedUser();

        if (user.isBlocked()) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("blocked.xhtml");
        }
    }
}
