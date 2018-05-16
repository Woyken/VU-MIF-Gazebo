package lt.vu.mif.repository.repository.implementations;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.model.product.Product;

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

    @ManyToOne(cascade = ALL)
    @JoinColumn(name = "PARENT_CATEGORY_ID")
    private Category parentCategory;

    @ElementCollection(fetch = FetchType.LAZY)
    @JoinTable(name = "CATEGORY_ATTRIBUTE", joinColumns = @JoinColumn(name = "CATEGORY_ID"))
    @Column(name = "ATTRIBUTE")
    private List<String> attributes = new ArrayList<>();

    @OneToMany(fetch = LAZY, cascade = ALL, mappedBy = "category")
    private List<Product> products = new ArrayList<>();

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }
}