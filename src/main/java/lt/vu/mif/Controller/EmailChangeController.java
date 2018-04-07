package lt.vu.mif.Controller;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.Entity.User;
import lt.vu.mif.Repository.UserRepository;
import lt.vu.mif.Service.UserService;

@Named
@Getter
@Setter
public class EmailChangeController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    private String loggedUserEmail;
    private String newEmail = null;

    public void onPageLoad() {
        loggedUserEmail = userService.getLoggedUserUserEmail();
    }

    public void updateUser() {
        User loggedUser = userService.getLoggedUser();
        loggedUser.setEmail(newEmail);

        userRepository.update(loggedUser);
    }
}