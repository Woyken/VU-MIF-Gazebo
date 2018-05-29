package lt.vu.mif.ui.helpers.interfaces;

import lt.vu.mif.excel.ExportResult;
import org.primefaces.model.StreamedContent;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IProductExportHelper {

    CompletableFuture<ExportResult> exportAllProducts();

    CompletableFuture<ExportResult> exportSelectedProducts(List<Long> productsIds);

    StreamedContent convertFile(byte[] bytes);
}