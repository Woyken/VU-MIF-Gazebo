package lt.vu.mif.ui.validation;

import lt.vu.mif.repository.repository.interfaces.IUserRepository;
import lt.vu.mif.utils.validation.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserRegistrationValidation implements Validator {

    @Autowired
    private IUserRepository userRepository;

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o)
            throws ValidatorException {

        String email = (String) ((UIInput) uiComponent.getAttributes().get("email")).getValue();
        String password = (String) ((UIInput) uiComponent.getAttributes().get("password"))
                .getValue();
        String passwordRepeat = (String) ((UIInput) uiComponent.getAttributes()
                .get("passwordRepeat")).getValue();

        if (email.isEmpty() || password.isEmpty() || passwordRepeat.isEmpty()) {
            throw new ValidatorException(new FacesMessage("Užpildyti ne visi privalomi laukai"));
        }

        if (!ValidationUtils.isEmailValid(email)) {
            throw new ValidatorException(new FacesMessage("Įvestas neteisingas el. pašto adresas"));
        }

        if (!ValidationUtils.isPasswordValid(password)) {
            throw new ValidatorException(new FacesMessage("Slaptažodis neatitinka reikalavimų"));
        }

        if (!password.equals(passwordRepeat)) {
            throw new ValidatorException(new FacesMessage("Įvesti slaptažodžiai nesutampa"));
        }

        if (userRepository.checkIfUserExists(email)) {
            throw new ValidatorException(
                    new FacesMessage("Naudotojas su nurodytu el. pašto adresu jau egzistuoja"));
        }
    }
}
