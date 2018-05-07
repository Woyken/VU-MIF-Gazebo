package lt.vu.mif.model.user;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class UserTokenTuple {

    private String userEmail;
    private String token;
    private LocalDateTime creationDate;

    public UserTokenTuple(String userEmail, LocalDateTime creationDate, String token) {
        this.userEmail = userEmail;
        this.token = token;
        this.creationDate = creationDate;
    }
}