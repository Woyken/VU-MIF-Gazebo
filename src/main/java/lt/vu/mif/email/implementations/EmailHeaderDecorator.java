package lt.vu.mif.email.implementations;

import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.email.interfaces.IEmailContentGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class EmailHeaderDecorator implements IEmailContentGenerator {
    private IEmailContentGenerator emailContentGenerator;

    @Value("headerText")
    private String headerText;

    @Override
    public String createPasswordRemindEmailBody(String token) {
        return headerText + " \n" + emailContentGenerator.createPasswordRemindEmailBody(token);
    }
}