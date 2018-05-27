package lt.vu.mif.utils.implementations;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import lt.vu.mif.utils.interfaces.IImageDownloader;
import org.apache.poi.util.IOUtils;
import org.springframework.stereotype.Component;

@Component
public class ImageDownloader implements IImageDownloader {

    public byte[] downloadImage(String imageLink) throws IOException {
        InputStream in = new URL(imageLink).openStream();
        byte[] result = IOUtils.toByteArray(in);
        in.close();
        return result;
    }
}