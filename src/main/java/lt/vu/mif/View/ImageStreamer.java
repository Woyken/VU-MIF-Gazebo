package lt.vu.mif.View;

import lombok.Getter;
import lt.vu.mif.Entity.Image;
import lt.vu.mif.Repository.ImageRepository;
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
    private ImageRepository imageRepository;

    public StreamedContent getImage() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            return new DefaultStreamedContent();
        } else {
            String id = context.getExternalContext().getRequestParameterMap().get("id");
            Image image = imageRepository.get(Image.class, Long.valueOf(id));
            return new DefaultStreamedContent(new ByteArrayInputStream(image.getContent()));
        }
    }
}