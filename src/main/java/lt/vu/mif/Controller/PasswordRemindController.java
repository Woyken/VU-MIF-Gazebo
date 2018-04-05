package lt.vu.mif.Controller;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.Constants;
import lt.vu.mif.Entity.User;
import lt.vu.mif.Repository.UserRepository;
import lt.vu.mif.Utils.ValidationUtils;

@Getter
@Setter
@Named
public class PasswordRemindController implements Serializable  {
    private static final String INVALID_LINK_MESSAGE = "Neteisinga nuoroda";
    private static final String LINK_EXPIRED_MESSAGE = "Nuorodos galiojimo laikas baigėsi";
    public static final int PASSWORD_LINK_EXPIRATION_TIME_IN_MINUTES = 5;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private String password;
    private String passwordRepeat;

    private String message;
    private User user;
    private String encryptedLink;

    private boolean success;
    private String errorMessage;

    public void onPageLoad() {
        FacesContext fc = FacesContext.getCurrentInstance();
        if (fc.getPartialViewContext().isAjaxRequest()) {
            // ignore this request, we are only interested in GET full page requests
            return;
        }

        encryptedLink = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(Constants.PASSWORD_REQUEST_PARAMETER);

        if (encryptedLink == null) {
            message = INVALID_LINK_MESSAGE;
            return;
        }

        user = userRepository.getUserByToken(encryptedLink);
        if (user == null) {
            message = INVALID_LINK_MESSAGE;
            return;
        }

        if (LocalDateTime.now().isAfter(user.getTokenCreationDate().plusMinutes(PASSWORD_LINK_EXPIRATION_TIME_IN_MINUTES))) {
            message = LINK_EXPIRED_MESSAGE;
        }
    }

    public void save() {
        if (ValidationUtils.isPasswordValid(password)) {
            user.setPassword(passwordEncoder.encode(password));
            user.setTokenCreationDate(null);
            user.setPasswordToken(null);
            userRepository.update(user);
        }
    }
}