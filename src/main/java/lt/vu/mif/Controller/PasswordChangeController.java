package lt.vu.mif.Controller;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.Entity.User;
import lt.vu.mif.Repository.UserRepository;
import lt.vu.mif.Service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.context.annotation.RequestScope;

@Named
@Getter
@Setter
@RequestScope
public class PasswordChangeController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private String password;
    private String successMessage;

    public void changePassword() {
        User user = userService.getLoggedUser();
        user.setPassword(passwordEncoder.encode(password));
        userRepository.update(user);
        successMessage = "Slaptažodis sėkmingai pakeistas";
    }
}