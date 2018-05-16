package lt.vu.mif.ui.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.ExecutionException;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.servlet.http.Part;
import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.ui.helpers.interfaces.IProductHelper;
import org.springframework.beans.factory.annotation.Autowired;

@Named
@Getter
@Setter
@SessionScoped
public class ProductImportController implements Serializable {

    @Autowired
    private IProductHelper productHelper;

    private Part uploadedFile;
    private String message;

    public void importProducts() {
        try {
            if (null == uploadedFile) {
                message = "Nepasirinktas failas.";
                return;
            }
            if (uploadedFile.getInputStream() != null) {
                message = "Importavimas vykdomas.";
                productHelper.importProducts(uploadedFile.getInputStream()).get();
                message = "Importavimas sėkmingai atliktas.";
            }
        } catch (ExecutionException | InterruptedException | IOException ex) {
            ex.printStackTrace();
            message = "Įvyko klaida bandant importuoti failą";
        }
    }
}