package lt.vu.mif.ui.helpers.implementations;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import lt.vu.mif.ui.helpers.interfaces.IImageHelper;
import lt.vu.mif.ui.view.ImageView;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class ImageHelper implements IImageHelper {

    @Override
    public ImageView getDefaultImage() {
        ImageView image = null;
        try {
            File file = new ClassPathResource("static/images/products/default.jpg").getFile();
            byte[] content = Files.readAllBytes(file.toPath());
            image = new ImageView();
            image.setBytes(content);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return image;
    }
}
