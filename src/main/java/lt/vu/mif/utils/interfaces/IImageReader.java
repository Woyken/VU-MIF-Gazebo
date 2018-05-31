package lt.vu.mif.utils.interfaces;

import java.io.IOException;

public interface IImageReader {

    byte[] downloadImage(String imageLink) throws IOException;

    byte[] readImageFromFile(String path) throws IOException;
}
