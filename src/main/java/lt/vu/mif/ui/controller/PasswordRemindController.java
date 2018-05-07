package lt.vu.mif.ui.controller;

import lt.vu.mif.email.interfaces.IEmailContentGenerator;
import lt.vu.mif.ui.helpers.interfaces.IUserHelper;

import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.RequestScope;

import lombok.Getter;
import lombok.Setter;

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
            userHelper.updateUserToken(email);
            message = "Laiškas su nuoroda sėkmingai nusiųstas";
        } else {
            message = "Naudotojas su nurodytu el. paštu neegzistuoja";
        }
    }
}

