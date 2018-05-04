package lt.vu.mif.Utils;

import java.util.ArrayList;
import java.util.List;
import lt.vu.mif.Entity.Image;
import lt.vu.mif.Entity.Product;

public class ProductParser {

    public static List<Product> parseProducts(List<ExcelProduct> excelProducts) {
        List<Product> result = new ArrayList<>();

        for (ExcelProduct excelProduct : excelProducts) {
            Product product = new Product();
            product.setSku(excelProduct.getSkuCode());
            product.setTitle(excelProduct.getTitle());
            product.setDescription(excelProduct.getDescription());
            product.setPrice(excelProduct.getPrice());
            product.getImages().add(parseImage(excelProduct.getImageLink()));
            result.add(product);
        }
        return result;
    }

    private static Image parseImage(String imageLink) {
        Image image = new Image();
        image.setContent(ImageDownloader.downloadImage(imageLink));
        return image;
    }
}