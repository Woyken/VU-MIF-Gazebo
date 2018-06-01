package lt.vu.mif.model.product;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "PRODUCT_ATTRIBUTE_VALUE")
public class CategoryAttributeValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "VALUE")
    private String value;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "CATEGORY_ATTRIBUTE_ID")
    private CategoryAttribute categoryAttribute;
}