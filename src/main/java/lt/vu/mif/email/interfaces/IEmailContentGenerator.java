package lt.vu.mif.email.interfaces;

public interface IEmailContentGenerator {

    String createPasswordRemindEmailBody(String token);
}
