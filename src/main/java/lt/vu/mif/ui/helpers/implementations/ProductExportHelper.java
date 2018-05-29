package lt.vu.mif.ui.helpers.implementations;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import lt.vu.mif.excel.ExportResult;
import lt.vu.mif.excel.ProductExcelWriter;
import lt.vu.mif.model.product.Product;
import lt.vu.mif.repository.repository.interfaces.IProductRepository;
import lt.vu.mif.ui.helpers.interfaces.IProductExportHelper;
import lt.vu.mif.utils.constants.Constants;
import org.apache.commons.codec.Charsets;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductExportHelper implements IProductExportHelper {

    @Autowired
    private ProductExcelWriter productExcelWriter;
    @Autowired
    private IProductRepository productRepository;

    @Override
    public CompletableFuture<ExportResult> exportAllProducts() {
        return exportProducts(productRepository.findAll());
    }

    @Override
    public CompletableFuture<ExportResult> exportSelectedProducts(List<Long> productsIds) {
        return exportProducts(productRepository.get(productsIds));
    }

    @Override
    public StreamedContent convertFile(byte[] bytes) {
        ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
        return new DefaultStreamedContent(stream, Constants.ZIP_TYPE, Constants.ZIP_NAME,
            Charsets.UTF_8.name());
    }

    private CompletableFuture<ExportResult> exportProducts(List<Product> products) {
        CompletionStage<ExportResult> productPromise = productExcelWriter.exportProducts(products);
        return productPromise.toCompletableFuture();
    }

}