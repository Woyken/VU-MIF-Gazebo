package lt.vu.mif.utils.implementations;

import lt.vu.mif.utils.interfaces.IImageReader;
import org.apache.poi.util.IOUtils;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Component
public class ImageReader implements IImageReader {

    public byte[] downloadImage(String imageLink) throws IOException {
        InputStream in = new URL(imageLink).openStream();
        byte[] result = IOUtils.toByteArray(in);
        in.close();
        return result;
    }

    public byte[] readImageFromFile(String path) throws IOException {
        File file = new File(path);

        BufferedImage image = ImageIO.read(file);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", byteArrayOutputStream);

        return byteArrayOutputStream.toByteArray();
    }
}