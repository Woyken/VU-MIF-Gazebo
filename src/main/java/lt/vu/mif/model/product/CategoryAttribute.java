package lt.vu.mif.model.product;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.MERGE;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "CATEGORY_ATTRIBUTE")
public class CategoryAttribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @ManyToOne(cascade = MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    @OneToMany(cascade = ALL, fetch = FetchType.LAZY, mappedBy = "categoryAttribute", orphanRemoval = true)
    private List<CategoryAttributeValue> values = new ArrayList<>();

}