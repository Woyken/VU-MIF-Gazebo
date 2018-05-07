package lt.vu.mif.ui.validation;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import lt.vu.mif.authentication.UserService;
import lt.vu.mif.utils.validation.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Named
@Component
public class ChangeEmailValidation implements Validator {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {

        String newEmail = (String) ((UIInput) uiComponent.getAttributes().get("newEmail")).getValue();
        String newEmailRepeat = (String) ((UIInput) uiComponent.getAttributes().get("newEmailRepeat")).getValue();
        String password = (String) ((UIInput) uiComponent.getAttributes().get("password")).getValue();

        if (newEmail.isEmpty() || newEmailRepeat.isEmpty() || password.isEmpty()) {
            throw new ValidatorException(new FacesMessage("Užpildyti ne visi privalomi laukai"));
        }

        if (!ValidationUtils.isEmailValid(newEmail)) {
            throw new ValidatorException(new FacesMessage("Įvestas neteisingas el. pašto adresas"));
        }

        if (!newEmail.equals(newEmailRepeat)) {
            throw new ValidatorException(new FacesMessage("Įvesti el. pašto adresai nesutampa"));
        }

        if(!passwordEncoder.matches(password, userService.getLoggedUser().getPassword())) {
            throw new ValidatorException(new FacesMessage("Įvestas neteisingas slaptažodis"));
        }
    }
}
