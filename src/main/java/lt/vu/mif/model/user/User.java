package lt.vu.mif.model.user;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "USER")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "HASHED_PASSWORD", nullable = false)
    private String password;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "ROLE", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "PASSWORD_TOKEN")
    private String passwordToken;

    @Column(name = "TOKEN_CREATION_DATE")
    private LocalDateTime tokenCreationDate;

    @Column(name = "IS_BLOCKED")
    private boolean blocked;

    public User() {
    }

    public User(String password, String email, Role role) {
        this.password = password;
        this.email = email;
        this.role = role;
    }
}