package lt.vu.mif.Excel;

import lt.vu.mif.utils.interfaces.IImageReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ImageDownloadTest {

    private static final String IMAGE_LINK = "https://ibb.co/bRtceS";

    @Autowired
    private IImageReader imageReader;

    @Test
    public void downloadImageTest() throws Exception {
        imageReader.downloadImage(IMAGE_LINK);
    }
}