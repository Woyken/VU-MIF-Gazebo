package lt.vu.mif.Controller;

import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.Entity.Roles;
import lt.vu.mif.Entity.User;
import lt.vu.mif.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.RequestScope;

@Named
@Getter
@Setter
@RequestScope
public class UserRegistrationController {

    @Autowired
    private UserService userService;

    private String email;
    private String password;
    private String passwordRepeat;

    private String message;

    public void saveUser() {
        userService
            .save(new User(password, email, Roles.Role.USER));
        message = "Naudotojas " + email + " sėkmingai sukurtas";
    }
}