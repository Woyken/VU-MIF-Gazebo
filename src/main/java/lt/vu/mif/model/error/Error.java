package lt.vu.mif.model.error;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "TABLE")
@Entity
public class Error {

    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "ERROR_MESSAGE", nullable = false, length = 2000)
    private String message;

    @Column(name = "DATE", nullable = false)
    private LocalDateTime localDateTime = LocalDateTime.now();

    @Column(name = "LOGGER_USER_EMAIL")
    private String loggedUserEmail;
}