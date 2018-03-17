package lt.vu.mif.Email;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

class EmailProviderTests {

    private static final String TEST_EMAIL_TO = "fake.email@something.com";
    private static final String TEST_EMAIL_SUBJECT = "Some test subject";
    private static final String TEST_EMAIL_BODY = "Testing this mail";

    @Test
    void sendSimpleMailTo_sendingMail_success() {
        JavaMailSender javaMailSenderMock = Mockito.mock(JavaMailSender.class);

        EmailProvider emailProvider = new EmailProvider(javaMailSenderMock);
        emailProvider.sendSimpleMailTo(TEST_EMAIL_TO, TEST_EMAIL_SUBJECT, TEST_EMAIL_BODY);

        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        Mockito.verify(javaMailSenderMock).send(captor.capture());
        SimpleMailMessage captured = captor.getValue();
        Assertions.assertEquals(1, captured.getTo().length);
        Assertions.assertEquals(TEST_EMAIL_TO, captured.getTo()[0]);
        Assertions.assertEquals(TEST_EMAIL_SUBJECT, captured.getSubject());
        Assertions.assertEquals(TEST_EMAIL_BODY, captured.getText());
    }
}