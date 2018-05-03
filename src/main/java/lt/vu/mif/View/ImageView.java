package lt.vu.mif.View;

import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.Entity.Image;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import javax.inject.Named;
import java.io.ByteArrayInputStream;

@Named
@Setter
@Getter
public class ImageView {

    private Long id;
    private byte[] bytes;

    public ImageView() {
    }

    public ImageView(Image image) {
        this.id = image.getId();
        this.bytes = image.getContent();
    }

    public StreamedContent getContent() {
        return new DefaultStreamedContent(new ByteArrayInputStream(bytes));
    }
}