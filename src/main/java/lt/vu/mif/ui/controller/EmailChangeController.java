package lt.vu.mif.ui.controller;

import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.authentication.UserService;
import lt.vu.mif.ui.helpers.interfaces.IUserHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.RequestScope;

@Named
@Getter
@Setter
@RequestScope
public class EmailChangeController {

    @Autowired
    private UserService userService;
    @Autowired
    private IUserHelper userHelper;

    private String loggedUserEmail;
    private String newEmail;
    private String successMessage;

    public void changeEmail() {
        userHelper.updateUserEmail(userService.getLoggedUserEmail(), newEmail);
        successMessage = "El. paštas sėkmingai pakeistas";
    }
}