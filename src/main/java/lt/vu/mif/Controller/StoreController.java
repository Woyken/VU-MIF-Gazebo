package lt.vu.mif.Controller;

import java.time.LocalDateTime;

import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.RequestScope;

import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.Constants;
import lt.vu.mif.Email.EmailProvider;
import lt.vu.mif.Entity.User;
import lt.vu.mif.Repository.UserRepository;
import lt.vu.mif.Service.UserService;
import lt.vu.mif.TokenGenerator;

@Named
@Getter
@Setter
@RequestScope
public class StoreController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private EmailProvider emailProvider;

    private String email;
    private String message;

    public void remindPassword() {
        if (StringUtils.isNotBlank(email) && userRepository.checkIfUserExists(email)) {
            String token = TokenGenerator.generateToken();
            String emailContent= "Slaptažodį pakeisti galite:" + "<a href=\"http://localhost:8080/password-remind.xhtml?" + Constants.PASSWORD_REQUEST_PARAMETER + "=" + token + "\">čia</a>";

            User user = userRepository.getUserByEmail(email);
            user.setTokenCreationDate(LocalDateTime.now());
            user.setPasswordToken(token);
            userRepository.update(user);

            emailProvider.sendSimpleMailTo(email, "Slaptažodžio pakeitimas", emailContent);
            message = "Laiškas su nuoroda sėkmingai nusiųstas";
        } else {
            message = "Naudotojas su nurodytu el. paštu neegzistuoja";
        }
    }
}

