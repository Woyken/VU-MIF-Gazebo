package lt.vu.mif.ui.controller;

import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.Logging.Logged;
import lt.vu.mif.excel.ExportResult;
import lt.vu.mif.repository.repository.implementations.ProductRepository;
import lt.vu.mif.ui.helpers.interfaces.IProductExportHelper;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Logged
@Setter
@Getter
@ViewScoped
@Named
public class ProductExportController {

    @Autowired
    private IProductExportHelper productExportHelper;

    private byte[] exportFile;

    @Autowired
    private ProductRepository productRepository;

    private String message;

    public void onPageLoad() {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            return;
        }
        message = null;
    }

    public void exportAll() throws InterruptedException, ExecutionException {
        ExportResult result = productExportHelper.exportAllProducts().get();
        setDataAfterExport(result);
    }

    public void exportSelected(List<Long> ids) throws InterruptedException, ExecutionException {
        ExportResult result = productExportHelper
                .exportSelectedProducts(ids).get();

        setDataAfterExport(result);
    }

    private void setDataAfterExport(ExportResult result) {
        exportFile = result.getFile();
        if (result.getMessage() != null) {
            message = result.getMessage();
        } else {
            message = "Eksportavimas sėkmingai atliktas.";
        }
    }

    public StreamedContent getFile() {
        return productExportHelper.convertFile(exportFile);
    }
}
