package lt.vu.mif.Email;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class EmailProviderTests {

    @Test
    public void testEmail() {
        JavaMailSender javaMailSenderMock = Mockito.mock(JavaMailSender.class);

        EmailProvider emailProvider = new EmailProvider(javaMailSenderMock);
        emailProvider.sendMail("karolis.uzkuraitis@gmail.com", "Test subject", "Test mail");

        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        Mockito.verify(javaMailSenderMock).send(captor.capture());
        SimpleMailMessage captured = captor.getValue();
        Assertions.assertEquals(1, captured.getTo().length);
        Assertions.assertEquals("karolis.uzkuraitis@gmail.com", captured.getTo()[0]);
        Assertions.assertEquals("Test subject", captured.getSubject());
        Assertions.assertEquals("Test mail", captured.getText());
    }
}