package lt.vu.mif.Validation;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import lt.vu.mif.Entity.User;
import lt.vu.mif.Service.UserService;
import lt.vu.mif.Utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Named
@Component
public class ChangePasswordValidation implements Validator {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserService userService;

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

        User user = userService.getLoggedUser();

        if(!passwordEncoder.matches(oldPassword, userService.getLoggedUser().getPassword())) {
            throw new ValidatorException(new FacesMessage("Įvestas neteisingas dabartinis slaptažodis"));
        }
    }
}
