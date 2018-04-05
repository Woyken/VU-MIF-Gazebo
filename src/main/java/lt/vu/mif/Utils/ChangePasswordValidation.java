package lt.vu.mif.Utils;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value = "ChangePasswordValidation")
public class ChangePasswordValidation implements Validator {

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o)
        throws ValidatorException {

        String newPassword = (String) ((UIInput) uiComponent.getAttributes().get("newPassword"))
            .getValue();
        String newPasswordRepeat = (String) ((UIInput) uiComponent.getAttributes()
            .get("newPasswordRepeat")).getValue();
        String oldPassword = (String) ((UIInput) uiComponent.getAttributes().get("oldPassword"))
            .getValue();

        if (newPassword.isEmpty() || newPasswordRepeat.isEmpty() || oldPassword.isEmpty()) {
            throw new ValidatorException(new FacesMessage("Užpildyti ne visi privalomi laukai"));
        }

        if (!ValidationUtils.isPasswordValid(newPassword)) {
            throw new ValidatorException(new FacesMessage("Slaptažodis neatitinka reikalavimų"));
        }

        if (!newPassword.equals(newPasswordRepeat)) {
            throw new ValidatorException(new FacesMessage("Įvesti slaptažodžiai nesutampa"));

        }
    }
}
