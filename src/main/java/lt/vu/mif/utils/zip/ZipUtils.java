package lt.vu.mif.utils.zip;

import lt.vu.mif.model.product.Image;
import lt.vu.mif.utils.constants.Constants;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
@Transactional
public class ZipUtils {

    public byte[] createZipForExport(List<Image> images) {
        byte[] bytes = null;

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream(); ZipOutputStream zos = new ZipOutputStream(
                baos)) {

            addImages(images, zos);
            addExcelFile(zos);

            closeZipOutputStream(zos);
            bytes = baos.toByteArray();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return bytes;
    }

    private void addImages(List<Image> images, ZipOutputStream zos)
            throws IOException {
        for (Image image : images) {
            ZipEntry entry = new ZipEntry(
                    Constants.IMAGES_FOLDER + Constants.IMAGE_PREFIX + image.getId()
                            + Constants.IMAGE_TYPE);
            zos.putNextEntry(entry);
            zos.write(image.getContent());
        }
    }

    private void addExcelFile(ZipOutputStream zos)
            throws IOException {
        Path path = Paths.get(Constants.EXCEL_FILE_PATH);
        byte[] data = Files.readAllBytes(path);

        ZipEntry entry = new ZipEntry(Constants.EXCEL_EXPORT_FILE_NAME);
        zos.putNextEntry(entry);
        zos.write(data);
    }

    //You must call this otherwise zip will be corrupted
    private void closeZipOutputStream(ZipOutputStream zos) throws IOException {
        zos.finish();
        zos.flush();
        zos.closeEntry();
    }
}
