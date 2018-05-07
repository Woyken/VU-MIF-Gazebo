package lt.vu.mif.email.implementations;

import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lt.vu.mif.email.interfaces.IEmailProvider;

@Service
public class EmailProvider implements IEmailProvider {

    private JavaMailSender javaMailSender;

    @Autowired
    public EmailProvider(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendSimpleMailTo(String toEmail, String subject, String htmlMessage) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            mimeMessage.setContent(htmlMessage, "text/html; charset=UTF-8");
            mimeMessage.setRecipient(RecipientType.TO, InternetAddress.parse(toEmail)[0]);
            mimeMessage.setSubject(subject);
            javaMailSender.send(mimeMessage);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
