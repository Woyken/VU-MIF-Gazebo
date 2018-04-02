package lt.vu.mif.Entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.Entity.Roles.Role;

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
}