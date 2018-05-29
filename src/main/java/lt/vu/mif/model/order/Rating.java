package lt.vu.mif.model.order;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "RATING")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "DATE", nullable = false)
    private LocalDateTime date;

    @Column(name = "COMMENT")
    private String comment;

    @Column(name = "VALUE", nullable = false)
    private Long value;

    @OneToOne
    @JoinColumn(name = "ORDER_ID")
    private Order order;
}