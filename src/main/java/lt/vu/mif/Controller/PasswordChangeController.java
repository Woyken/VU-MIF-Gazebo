package lt.vu.mif.Controller;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.Entity.User;
import lt.vu.mif.Repository.UserRepository;
import lt.vu.mif.Service.UserService;

@Named
@Getter
@Setter
public class PasswordChangeController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    private String password;

    public void changePassword() {
        User user = userService.getLoggedUser();
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        userRepository.update(user);
    }
}