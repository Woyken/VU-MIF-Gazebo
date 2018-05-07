package lt.vu.mif.utils.implementations;

import java.util.ArrayList;
import java.util.List;
import lt.vu.mif.excel.ExcelProduct;
import lt.vu.mif.model.product.Image;
import lt.vu.mif.model.product.Product;
import lt.vu.mif.utils.interfaces.IImageDownloader;
import lt.vu.mif.utils.interfaces.IProductParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductParser implements IProductParser {

    @Autowired
    private IImageDownloader imageDownloader;

    public List<Product> parseProducts(List<ExcelProduct> excelProducts) {
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

    private Image parseImage(String imageLink) {
        Image image = new Image();
        image.setContent(imageDownloader.downloadImage(imageLink));
        return image;
    }
}