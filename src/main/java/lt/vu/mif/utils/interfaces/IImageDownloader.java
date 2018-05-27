package lt.vu.mif.utils.interfaces;

import java.io.IOException;

public interface IImageDownloader {

    byte[] downloadImage(String imageLink) throws IOException;
}
