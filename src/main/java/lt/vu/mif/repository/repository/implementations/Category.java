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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
    @JoinColumn(name = "SUBCATEGORY_ID")
    private Category subcategory;

    @ElementCollection(fetch = FetchType.LAZY)
    @JoinTable(name = "CATEGORY_ATTRIBUTE", joinColumns = @JoinColumn(name = "CATEGORY_ID"))
    @Column(name = "ATTRIBUTE")
    private List<String> attributes = new ArrayList<>();

    @ManyToMany(fetch = LAZY, cascade = ALL)
    @JoinTable(name = "CATEGORY_PRODUCTS", joinColumns = @JoinColumn(name = "CATEGORY_ID"), inverseJoinColumns = @JoinColumn(name = "PRODUCT_ID"))
    private List<Product> products = new ArrayList<>();
}