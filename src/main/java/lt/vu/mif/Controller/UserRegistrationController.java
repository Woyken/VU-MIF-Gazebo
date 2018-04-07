package lt.vu.mif.Controller;

import java.util.Objects;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.Entity.Roles;
import lt.vu.mif.Entity.User;
import lt.vu.mif.Repository.UserRepository;
import lt.vu.mif.Utils.ValidationUtils;

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
        boolean passwordValid = ValidationUtils.isPasswordValid(password);
        boolean passwordsMatch = Objects.equals(password, passwordRepeat);
        boolean emailValid = ValidationUtils.isEmailValid(email);
        boolean userAlreadyExists = userRepository.checkIfUserExists(email);

        if (passwordValid && passwordsMatch && emailValid && !userAlreadyExists) {
            userRepository.save(new User(new BCryptPasswordEncoder().encode(password), email, Roles.Role.USER));
            message = "Naudotojas " + email + " sėkmingai sukurtas";
        }
    }
}