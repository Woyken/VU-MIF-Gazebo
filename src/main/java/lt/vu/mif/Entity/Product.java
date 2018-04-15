package lt.vu.mif.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "SKU", nullable = false)
    private String sku;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "PRICE", nullable = false)
    private BigDecimal price;

    @Column(name = "DESCRIPTION", length = 2000, nullable = false)
    private String description;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Image> images = new ArrayList<>();
}