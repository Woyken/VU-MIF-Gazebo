package lt.vu.mif.ui.controller;

import lt.vu.mif.bl.constants.Constants;
import lt.vu.mif.model.user.UserTokenTuple;
import lt.vu.mif.ui.helpers.interfaces.IUserHelper;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
@Named
public class PasswordUpdateController implements Serializable  {
    private static final String INVALID_LINK_MESSAGE = "Neteisinga nuoroda";
    private static final String LINK_EXPIRED_MESSAGE = "Nuorodos galiojimo laikas baigėsi";
    public static final int PASSWORD_LINK_EXPIRATION_TIME_IN_MINUTES = 5;

    private String password;
    private String message;
    private UserTokenTuple user;

    @Autowired
    private IUserHelper userHelper;

    public void onPageLoad() {
        message = null;
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            return;
        }

        String encryptedLink = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(Constants.PASSWORD_REQUEST_PARAMETER);

        if (encryptedLink == null) {
            message = INVALID_LINK_MESSAGE;
            return;
        }

        user = userHelper.getTokenCreationDate(encryptedLink);
        if (user == null) {
            message = INVALID_LINK_MESSAGE;
            return;
        }

        if (LocalDateTime.now().isAfter(user.getCreationDate().plusMinutes(PASSWORD_LINK_EXPIRATION_TIME_IN_MINUTES))) {
            message = LINK_EXPIRED_MESSAGE;
        }
    }

    public String changePassword() {
        userHelper.changeUserPasswordAndDeleteToken(user.getUserEmail(), password);
        return "login.xhtml?faces-redirect=true";
    }
}