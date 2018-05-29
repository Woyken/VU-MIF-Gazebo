package lt.vu.mif.model.product;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Lob
    @Column(name = "CONTENT", nullable = false)
    private byte[] content;

    public Image() {
    }

    public Image(byte[] content) {
        this.content = content;
    }
}