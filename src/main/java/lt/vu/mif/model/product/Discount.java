package lt.vu.mif.model.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "DISCOUNT")
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "PERCENTAGE_DISCOUNT")
    private Long percentageDiscount;

    @Column(name = "ABSOLUTE_DISCOUNT")
    private BigDecimal absoluteDiscount;

    @Column(name = "VALID_FROM", nullable = false)
    private LocalDateTime from;

    @Column(name = "VALID_TO", nullable = false)
    private LocalDateTime to;

    @Transient
    public boolean isDiscountValid() {
        LocalDateTime now = LocalDateTime.now();
        return (now.isAfter(from) || now.isEqual(from)) && (now.isBefore(to) || now.isEqual(to));
    }
}