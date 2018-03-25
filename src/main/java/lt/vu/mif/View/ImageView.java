package lt.vu.mif.View;

import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.Entity.Image;

import javax.inject.Named;

@Named
@Setter
@Getter
public class ImageView {
    private Long id;
    private byte[] content;

    public ImageView() {
    }

    public ImageView(Image image) {
        this.id = image.getId();
        this.content = image.getContent();
    }
}