package lt.vu.mif.ui.view;

import java.io.ByteArrayInputStream;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@Named
@Setter
@Getter
public class ImageView {

    private Long id;
    private byte[] bytes;

    public StreamedContent getContent() {
        return new DefaultStreamedContent(new ByteArrayInputStream(bytes));
    }
}