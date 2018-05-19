package lt.vu.mif.model.order;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

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