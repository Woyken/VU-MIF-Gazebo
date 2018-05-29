package lt.vu.mif.ui.validation;

import lt.vu.mif.utils.validation.ValidationUtils;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

@Named
@Component
public class BuyProductValidation implements Validator {

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o)
            throws ValidatorException {

        String amount = (String) ((UIInput) uiComponent.getAttributes().get("amount")).getValue();

        if (amount.isEmpty()) {
            throw new ValidatorException(new FacesMessage("Prašome įvesti kiekį"));
        }

        if (!ValidationUtils.isAmountValid(amount)) {
            throw new ValidatorException(new FacesMessage("Įvestas netinkamas kiekis"));
        }
    }
}
