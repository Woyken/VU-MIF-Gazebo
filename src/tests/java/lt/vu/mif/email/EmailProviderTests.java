package lt.vu.mif.email;

import lt.vu.mif.email.implementations.EmailProvider;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.Address;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

@RunWith(SpringRunner.class)
public class EmailProviderTests {

    private static final String TEST_EMAIL_TO = "fake.email@something.com";
    private static final String TEST_EMAIL_SUBJECT = "Some test subject";
    private static final String TEST_EMAIL_BODY = "Testing this mail";

    @Test
    public void sendSimpleMailTo_sendingMail_success() {
        JavaMailSender javaMailSenderMock = Mockito.mock(JavaMailSender.class);
        Mockito.when(javaMailSenderMock.createMimeMessage()).thenReturn(new MimeMessage((Session) null));

        EmailProvider emailProvider = new EmailProvider(javaMailSenderMock);
        emailProvider.sendSimpleMailTo(TEST_EMAIL_TO, TEST_EMAIL_SUBJECT, TEST_EMAIL_BODY);

        ArgumentCaptor<MimeMessage> captor = ArgumentCaptor.forClass(MimeMessage.class);
        Mockito.verify(javaMailSenderMock).send(captor.capture());

        MimeMessage captured = captor.getValue();
        try {
            Assertions.assertEquals(1, captured.getRecipients(RecipientType.TO).length);
            Address[] allRecipients = captured.getRecipients(RecipientType.TO);
            Assertions.assertEquals(TEST_EMAIL_TO, allRecipients[0].toString());
            Assertions.assertEquals(TEST_EMAIL_SUBJECT, captured.getSubject());
            Assertions.assertEquals(TEST_EMAIL_BODY, captured.getContent().toString());
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
    }
}