package lt.vu.mif.model.product;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.FetchType.LAZY;

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

    @ElementCollection(fetch = FetchType.LAZY)
    @JoinTable(name = "CATEGORY_ATTRIBUTE", joinColumns = @JoinColumn(name = "CATEGORY_ID"))
    @Column(name = "ATTRIBUTE")
    private List<String> attributes = new ArrayList<>();

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