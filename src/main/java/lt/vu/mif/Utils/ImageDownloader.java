package lt.vu.mif.Utils;

import java.io.InputStream;
import java.net.URL;
import org.apache.poi.util.IOUtils;

public class ImageDownloader {
    public static byte[] downloadImage(String imageLink) {
        try(InputStream in = new URL(imageLink).openStream()){
            return IOUtils.toByteArray(in);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}