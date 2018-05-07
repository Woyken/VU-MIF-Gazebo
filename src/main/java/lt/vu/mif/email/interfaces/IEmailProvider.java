package lt.vu.mif.email.interfaces;

public interface IEmailProvider {
    void sendSimpleMailTo(String toEmail, String subject, String htmlMessage);
}
