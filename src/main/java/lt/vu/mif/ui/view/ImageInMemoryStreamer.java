package lt.vu.mif.ui.view;

import lombok.Getter;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Named;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Named
@Getter
@SessionScoped
public class ImageInMemoryStreamer implements Serializable {

    private Map<Long, InputStream> imagesInMemory = new HashMap<>();

    public StreamedContent getImage() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            return new DefaultStreamedContent();
        } else {
            String id = context.getExternalContext().getRequestParameterMap().get("id");

            if (id == null || !imagesInMemory.keySet().contains(Long.valueOf(id))) {
                return null;
            }

            return new DefaultStreamedContent(imagesInMemory.get(Long.valueOf(id)));
        }
    }

    public void addImage(Long id, InputStream inputStream) {
        imagesInMemory.put(id, inputStream);
    }
}