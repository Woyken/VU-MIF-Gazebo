package lt.vu.mif.email.implementations;

import lt.vu.mif.email.interfaces.IEmailContentGenerator;
import lt.vu.mif.utils.constants.Constants;
import org.springframework.stereotype.Component;

@Component
public class DefaultEmailContentGenerator implements IEmailContentGenerator {

    @Override
    public String createPasswordRemindEmailBody(String token) {
        String text = "Slaptažodį pakeisti galite:";
        String link = "<a href=\"http://" + Constants.ENVIRONMENT
            + "password-change.xhtml?" + Constants.PASSWORD_REQUEST_PARAMETER + "=" + token
            + "\">čia</a>";

        return text + link;
    }
}