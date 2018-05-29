package lt.vu.mif.ui.view;

import lombok.Getter;
import lt.vu.mif.model.product.Image;
import lt.vu.mif.repository.repository.interfaces.IImageRepository;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Named;
import java.io.ByteArrayInputStream;

@Named
@Getter
public class ImageStreamer {

    @Autowired
    private IImageRepository imageRepository;

    public StreamedContent getImage() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            return new DefaultStreamedContent();
        } else {
            String id = context.getExternalContext().getRequestParameterMap().get("id");

            if (id == null) {
                return null;
            }

            Image image = imageRepository.get(Long.valueOf(id));
            return new DefaultStreamedContent(new ByteArrayInputStream(image.getContent()));
        }
    }
}