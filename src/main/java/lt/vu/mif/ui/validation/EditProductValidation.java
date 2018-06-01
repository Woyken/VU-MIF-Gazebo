package lt.vu.mif.ui.validation;

import java.math.BigDecimal;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import lt.vu.mif.repository.repository.interfaces.IProductRepository;
import lt.vu.mif.ui.view.AttributeView;
import lt.vu.mif.utils.validation.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Named
@Component
public class EditProductValidation extends DiscountValidation implements Validator {

    @Autowired
    private IProductRepository productRepository;

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o)
        throws ValidatorException {

        try {
            super.validate(facesContext, uiComponent, o);
        } catch (Exception ex) {
            throw ex;
        }

        Long id = (Long) uiComponent.getAttributes().get("productId");
        String sku = (String) ((UIInput) uiComponent.getAttributes().get("sku"))
            .getValue();
        String title = (String) ((UIInput) uiComponent.getAttributes()
            .get("title")).getValue();
        BigDecimal price = (BigDecimal) ((UIInput) uiComponent.getAttributes()
            .get("price")).getValue();

        if (sku.isEmpty() || title.isEmpty()) {
            throw new ValidatorException(new FacesMessage("Užpildyti ne visi privalomi laukai"));
        }

        if (sku.length()>=255) {
            throw new ValidatorException(new FacesMessage("SKU kodas negali būti ilgesnis nei 255 simboliai"));
        }

        if (title.length()>=255) {
            throw new ValidatorException(new FacesMessage("Prekės pavadinimas negali būti ilgesnis nei 255 simboliai"));
        }

        if (price == null) {
            throw new ValidatorException(new FacesMessage("Tokia kaina yra negalima"));
        }

        if (ValidationUtils.getNumberOfDecimalPlaces(price) > 2) {
            throw new ValidatorException(
                new FacesMessage("Kainoje įvesta per daug skaičių po kablelio"));
        }

        if (price.signum() == -1) {
            throw new ValidatorException(new FacesMessage("Kaina negali būti neigiama"));
        }

        Long existingId = productRepository.getIdBySku(sku);

        if (existingId != null && !existingId.equals(id)) {
            throw new ValidatorException(
                new FacesMessage(
                    "Nurodytas SKU kodas jau egzistuoja sistemoje. Pateikite unikalų SKU kodą"));
        }

        List<AttributeView> allAttributes = (List<AttributeView>) uiComponent.getAttributes()
            .get("allCategoriesAttributes");

        for (AttributeView a : allAttributes) {
            if (a.getSelectedValue() == null) {
                throw new ValidatorException(
                    new FacesMessage("Požymiui \"" + a.getName() + "\" nepasirinkta reikšmė"));
            }
        }
    }
}
