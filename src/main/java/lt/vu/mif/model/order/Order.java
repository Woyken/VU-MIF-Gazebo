package lt.vu.mif.model.order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.model.user.User;

@Getter
@Setter
@Entity
@Table(name = "\"ORDER\"")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "CREATION_DATE", nullable = false)
    private LocalDateTime creationDate;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Column(name = "STATUS")
    @Enumerated(value = EnumType.STRING)
    private OrderStatus status;

    @OneToOne
    @JoinColumn(name="RATING")
    private OrderRating rating;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderProduct> products = new ArrayList<>();
}