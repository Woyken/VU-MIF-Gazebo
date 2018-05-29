package lt.vu.mif.email.implementations;

import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.email.interfaces.IEmailContentGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class EmailFooterGenerator implements IEmailContentGenerator {
    private IEmailContentGenerator emailContentGenerator;

    @Value("footerText")
    private String footerText;

    @Override
    public String createPasswordRemindEmailBody(String token) {
        return emailContentGenerator.createPasswordRemindEmailBody(token) + "\n" + footerText;
    }
}