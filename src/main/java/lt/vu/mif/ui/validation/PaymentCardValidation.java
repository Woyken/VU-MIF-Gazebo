package lt.vu.mif.ui.validation;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import lt.vu.mif.utils.validation.ValidationUtils;
import org.springframework.stereotype.Component;

@Named
@Component
public class PaymentCardValidation implements Validator {

    // Doesn't check if card's number satisfies Luhn's algorithm
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o)
        throws ValidatorException {

        String name = (String) ((UIInput) uiComponent.getAttributes().get("name"))
            .getValue();
        String surname = (String) ((UIInput) uiComponent.getAttributes().get("surname"))
            .getValue();
        String address = (String) ((UIInput) uiComponent.getAttributes().get("address"))
            .getValue();
        String cardNumber = (String) ((UIInput) uiComponent.getAttributes().get("cardNumber"))
            .getValue();
        String yearDesktop = (String) ((UIInput) uiComponent.getAttributes().get("yearDesktop"))
            .getValue();
        String monthDesktop = (String) ((UIInput) uiComponent.getAttributes().get("monthDesktop"))
            .getValue();
        String cvsDesktop = (String) ((UIInput) uiComponent.getAttributes().get("cvsDesktop"))
            .getValue();
        String yearMobile = (String) ((UIInput) uiComponent.getAttributes().get("yearMobile"))
            .getValue();
        String monthMobile = (String) ((UIInput) uiComponent.getAttributes().get("monthMobile"))
            .getValue();
        String cvsMobile = (String) ((UIInput) uiComponent.getAttributes().get("cvsMobile"))
            .getValue();

        String year = yearDesktop.isEmpty() ? yearMobile : yearDesktop;
        String month = monthDesktop.isEmpty() ? monthMobile : monthDesktop;
        String cvs = cvsDesktop.isEmpty() ? cvsMobile : cvsDesktop;

        if (name.isEmpty() || surname.isEmpty() || address.isEmpty() || cardNumber.isEmpty() || year
            .isEmpty() || month.isEmpty() || cvs.isEmpty()) {
            throw new ValidatorException(new FacesMessage("Užpildyti ne visi laukai"));
        }

        if (name.length() + surname.length() + 1 > 32) {
            throw new ValidatorException(new FacesMessage("Vardas ir pavardė per ilgi"));
        }

        if (!ValidationUtils.isPaymentCardValid(cardNumber)) {
            throw new ValidatorException(new FacesMessage("Neteisingas kortelės numeris"));
        }

        if (!ValidationUtils.isYearValid(year)) {
            throw new ValidatorException(new FacesMessage("Neteisinga YY reikšmė"));
        }

        if (!ValidationUtils.isMonthValid(month)) {
            throw new ValidatorException(new FacesMessage("Neteisinga MM reikšmė"));
        }

        if (!ValidationUtils.isCvsValid(cvs)) {
            throw new ValidatorException(new FacesMessage("Neteisinga CVS reikšmė"));
        }
    }
}
