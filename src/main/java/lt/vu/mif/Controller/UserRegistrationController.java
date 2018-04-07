package lt.vu.mif.Controller;

import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.Entity.Roles;
import lt.vu.mif.Entity.User;
import lt.vu.mif.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Named
@Getter
@Setter
public class UserRegistrationController {

    @Autowired
    private UserRepository userRepository;

    private String email;
    private String password;
    private String passwordRepeat;

    private String message;

    public void saveUser() {
        userRepository
            .save(new User(new BCryptPasswordEncoder().encode(password), email, Roles.Role.USER));
        message = "Naudotojas " + email + " sėkmingai sukurtas";
    }
}