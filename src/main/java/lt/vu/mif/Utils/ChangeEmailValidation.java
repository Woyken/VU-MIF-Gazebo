package lt.vu.mif.Utils;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value = "ChangeEmailValidation")
public class ChangeEmailValidation implements Validator {

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o)
        throws ValidatorException {

        String newEmail = (String) ((UIInput) uiComponent.getAttributes().get("newEmail"))
            .getValue();
        String newEmailRepeat = (String) ((UIInput) uiComponent.getAttributes()
            .get("newEmailRepeat")).getValue();
        String password = (String) ((UIInput) uiComponent.getAttributes().get("password"))
            .getValue();

        if (newEmail.isEmpty() || newEmailRepeat.isEmpty() || password.isEmpty()) {
            throw new ValidatorException(new FacesMessage("Užpildyti ne visi privalomi laukai"));
        }

        if (!ValidationUtils.isEmailValid(newEmail)) {
            throw new ValidatorException(new FacesMessage("Įvestas neteisingas el. pašto adresas"));
        }

        if (!newEmail.equals(newEmailRepeat)) {
            throw new ValidatorException(new FacesMessage("Įvesti el. pašto adresai nesutampa"));

        }
    }
}
