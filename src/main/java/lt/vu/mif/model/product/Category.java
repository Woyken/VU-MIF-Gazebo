package lt.vu.mif.model.product;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.FetchType.LAZY;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "CATEGORY")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "PARENT_CATEGORY_ID")
    private Category parentCategory;

    @OneToMany(cascade = ALL, fetch = LAZY, mappedBy = "category", orphanRemoval = true)
    private List<CategoryAttribute> attributes = new ArrayList<>();

    @OneToMany(cascade = MERGE, fetch = LAZY, mappedBy = "category")
    private List<Product> products = new ArrayList<>();

    @ManyToOne(cascade = ALL, fetch = LAZY)
    @JoinColumn(name = "DISCOUNT_ID")
    private Discount discount;

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }
}