package lt.vu.mif.ui.controller;

import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.Logging.Logged;
import lt.vu.mif.authentication.UserService;
import lt.vu.mif.ui.helpers.interfaces.IUserHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.annotation.RequestScope;

import javax.inject.Named;
import java.util.Collection;

@Logged
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

        // Update security context to hold new email with same authorities granted as before.
        Collection<? extends GrantedAuthority> currentAuthorities =
                SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(newEmail, null, currentAuthorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        successMessage = "El. paštas sėkmingai pakeistas";
    }
}