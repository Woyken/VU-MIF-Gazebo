package lt.vu.mif.email.implementations;

import lt.vu.mif.bl.constants.Constants;
import lt.vu.mif.email.interfaces.IEmailContentGenerator;
import org.springframework.stereotype.Component;

@Component
public class EmailContentGenerator implements IEmailContentGenerator {

    @Override
    public String createPasswordRemindEmailBody(String token) {
        return "Slaptažodį pakeisti galite:" + "<a href=\"http://" + Constants.ENVIRONMENT
                + "password-change.xhtml?" + Constants.PASSWORD_REQUEST_PARAMETER + "=" + token
                + "\">čia</a>";
    }
}