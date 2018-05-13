package lt.vu.mif.model.order;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class OrderRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "DESCRIPTION", length = 2000, nullable = false)
    private String description;

    @Column(name = "RATING", nullable = false)
    private long rating;

    @Column(name = "CREATION_DATE", nullable = false)
    private LocalDateTime creationDate;
}
