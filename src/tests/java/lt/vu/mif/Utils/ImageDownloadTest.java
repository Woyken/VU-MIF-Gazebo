package lt.vu.mif.Utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class ImageDownloadTest {
    private static final String IMAGE_LINK = "https://ibb.co/bRtceS";

    @Test
    public void downloadImageTest() {
        ImageDownloader.downloadImage(IMAGE_LINK);
    }
}
