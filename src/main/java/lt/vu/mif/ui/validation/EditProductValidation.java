package lt.vu.mif.ui.validation;

import java.math.BigDecimal;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

import lt.vu.mif.repository.repository.interfaces.IProductRepository;
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

        String sku = (String) ((UIInput) uiComponent.getAttributes().get("sku"))
            .getValue();
        String title = (String) ((UIInput) uiComponent.getAttributes()
            .get("title")).getValue();
        BigDecimal price = (BigDecimal) ((UIInput) uiComponent.getAttributes()
            .get("price")).getValue();

        if (sku.isEmpty() || title.isEmpty()) {
            throw new ValidatorException(new FacesMessage("Užpildyti ne visi privalomi laukai"));
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

        if (productRepository.checkIfProductExists(sku)) {
            throw new ValidatorException(
                    new FacesMessage("Nurodytas SKU kodas jau egzistuoja sisemoje. Pateikite unikalų SKU kodą"));
        }
    }
}
