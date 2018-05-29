package lt.vu.mif.model.user;

import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.model.order.Order;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();

    public User() {
    }

    public User(String password, String email, Role role) {
        this.password = password;
        this.email = email;
        this.role = role;
    }
}