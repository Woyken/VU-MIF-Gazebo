package lt.vu.mif.Controller;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.springframework.stereotype.Controller;

import lt.vu.mif.View.UserView;

/**
 * Created by monika on 18.3.20.
 */
@Named
@ViewScoped
@Controller
public class UserController {
    private UserView userView = new UserView(10251L, "test@gmail.com");

    public UserView getUserView() {
        return userView;
    }
}