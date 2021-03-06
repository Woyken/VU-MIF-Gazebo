package lt.vu.mif.model.product;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.MERGE;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "SKU", nullable = false, unique = true)
    private String sku;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PRICE", nullable = false)
    private BigDecimal price;

    @Column(name = "DESCRIPTION", length = 2000, nullable = false)
    private String description;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Image> images = new ArrayList<>();

    @Column(name = "IS_DELETED")
    private boolean deleted;

    @Column(name = "CREATION_DATE")
    private LocalDateTime creationDate;

    @ManyToOne(cascade = MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "DISCOUNT_ID")
    private Discount discount;

    @Version
    private Integer version;

    @OneToMany(fetch = FetchType.LAZY, cascade = ALL, mappedBy = "product", orphanRemoval = true)
    private List<ProductAttributeValue> attributeValues = new ArrayList<>();
}