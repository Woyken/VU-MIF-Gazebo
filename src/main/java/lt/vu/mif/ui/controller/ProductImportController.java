package lt.vu.mif.ui.controller;

import lt.vu.mif.ui.helpers.interfaces.IProductHelper;
import java.io.IOException;
import javax.inject.Named;
import javax.servlet.http.Part;
import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.RequestScope;

@Named
@Getter
@Setter
@RequestScope
public class ProductImportController {
    @Autowired
    private IProductHelper productHelper;

    private Part uploadedFile;
    private String message;

    public void importProducts() {
        try {
            if (uploadedFile != null && uploadedFile.getInputStream() != null) {
                productHelper.importProducts(uploadedFile.getInputStream());
                message = "Importas sėkmingai atliktas";
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            message = "Įvyko klaida bandant importuoti failą";
        }
    }
}