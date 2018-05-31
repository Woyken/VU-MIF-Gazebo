package lt.vu.mif.ui.controller;

import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.Logging.Logged;
import lt.vu.mif.email.interfaces.IEmailContentGenerator;
import lt.vu.mif.ui.helpers.interfaces.IUserHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.RequestScope;

@Logged
@Named
@Getter
@Setter
@RequestScope
public class PasswordRemindController {

    @Autowired
    private IUserHelper userHelper;
    @Autowired
    private IEmailContentGenerator emailContentGenerator;

    private String email;
    private String message;

    public void remindPassword() {
        if (StringUtils.isNotBlank(email) && userHelper.checkIfUserExists(email)) {
            userHelper.remindPassword(email);
            message = "Laiškas su nuoroda sėkmingai nusiųstas";
        } else {
            message = "Naudotojas su nurodytu el. paštu neegzistuoja";
        }
    }
}

