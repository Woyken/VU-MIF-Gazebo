package lt.vu.mif.bl.implementations;

import java.io.InputStream;
import java.net.URL;

import lt.vu.mif.bl.interfaces.IImageDownloader;
import org.apache.poi.util.IOUtils;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
public class ImageDownloader implements IImageDownloader {
    public byte[] downloadImage(String imageLink) {
        try(InputStream in = new URL(imageLink).openStream()){
            return IOUtils.toByteArray(in);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}