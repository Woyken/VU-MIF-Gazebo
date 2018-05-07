package lt.vu.mif.ui.controller;

import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.ui.helpers.implementations.UserHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.RequestScope;

@Named
@Getter
@Setter
@RequestScope
public class UserRegistrationController {
    @Autowired
    private UserHelper userHelper;

    private String email;
    private String password;
    private String passwordRepeat;

    private String message;

    public void saveUser() {
        userHelper.saveNewUser(password, email);
        message = "Naudotojas " + email + " sėkmingai sukurtas";
    }
}