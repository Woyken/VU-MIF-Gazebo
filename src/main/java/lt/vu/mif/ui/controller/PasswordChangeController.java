package lt.vu.mif.ui.controller;

import lt.vu.mif.ui.helpers.interfaces.IUserHelper;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.RequestScope;

@Named
@Getter
@Setter
@RequestScope
public class PasswordChangeController {

    @Autowired
    private IUserHelper userHelper;

    private String password;
    private String successMessage;

    public void changePassword() {
        userHelper.changeCurrentUserPassword(password);
        successMessage = "Slaptažodis sėkmingai pakeistas";
    }
}