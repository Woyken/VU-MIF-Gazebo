package lt.vu.mif.ui.validation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
public class DiscountValidation implements Validator {

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o)
        throws ValidatorException {
        BigDecimal discountPrice = (BigDecimal) ((UIInput) uiComponent.getAttributes()
            .get("discountPrice"))
            .getValue();
        //Can't get string from number field, so have to use BigDecimal, because need to validate if
        //user entered a whole number
        BigDecimal discountPercentage = (BigDecimal) ((UIInput) uiComponent.getAttributes()
            .get("discountPercentage"))
            .getValue();
        String startDate = (String) ((UIInput) uiComponent.getAttributes().get("startDate"))
            .getValue();
        String startTime = (String) ((UIInput) uiComponent.getAttributes().get("startTime"))
            .getValue();
        String endDate = (String) ((UIInput) uiComponent.getAttributes().get("endDate"))
            .getValue();
        String endTime = (String) ((UIInput) uiComponent.getAttributes().get("endTime"))
            .getValue();

        if (discountPrice == null && discountPercentage == null || startDate.isEmpty() || endDate
            .isEmpty() || startTime.isEmpty() || endTime.isEmpty()) {
            throw new ValidatorException(new FacesMessage("Užpildyti ne visi privalomi laukai"));
        }

        if (discountPrice != null && discountPercentage != null) {
            throw new ValidatorException(new FacesMessage
                ("Negalima priskirti nuolaidos ir nauja kaina ir procentais"));
        }

        if (discountPercentage == null) {
            if (ValidationUtils.getNumberOfDecimalPlaces(discountPrice) > 2) {
                throw new ValidatorException(new FacesMessage
                    ("Nuolaidos kainoje įvesta per daug skaičių po kablelio"));
            }

            if (discountPrice.intValue() < 0) {
                throw new ValidatorException(
                    new FacesMessage("Nauja kaina turi būti teigiamas skaičius"));
            }
        } else {
            if (ValidationUtils.getNumberOfDecimalPlaces(discountPercentage) > 0) {
                throw new ValidatorException(
                    new FacesMessage("Nuolaida procentais turi būti sveikas skaičius"));
            }

            if (discountPercentage.intValue() < 0) {
                throw new ValidatorException(
                    new FacesMessage("Nuolaida procentais turi būti teigiamas skaičius"));
            }

            if (discountPercentage.intValue() > 100) {
                throw new ValidatorException(
                    new FacesMessage("Nuolaida procentais negali būti didesnė už 100"));
            }
        }

        if (!ValidationUtils.isTimeValid(startTime)) {
            throw new ValidatorException(new FacesMessage("Neteisingas pradžios laikas"));
        }

        if (!ValidationUtils.isTimeValid(endTime)) {
            throw new ValidatorException(new FacesMessage("Neteisingas pabaigos laikas"));
        }

        LocalDateTime startDateParsed;
        LocalDateTime endDateParsed;

        try {
            startDateParsed = LocalDateTime.parse(startDate + " " + startTime,
                DateTimeFormatter.ofPattern(ValidationUtils.DATETIME_FORMAT));
        } catch (DateTimeParseException e) {
            throw new ValidatorException(new FacesMessage("Neteisinga pradžios data"));
        }

        try {
            endDateParsed = LocalDateTime.parse(endDate + " " + endTime,
                DateTimeFormatter.ofPattern(ValidationUtils.DATETIME_FORMAT));
        } catch (DateTimeParseException e) {
            throw new ValidatorException(new FacesMessage("Neteisinga pabaigos data"));
        }

        if (startDateParsed.isBefore(LocalDateTime.now())) {
            throw new ValidatorException(new FacesMessage("Nuolaida negali prasidėti praeityje"));
        }

        if (endDateParsed.isBefore(startDateParsed)) {
            throw new ValidatorException(
                new FacesMessage("Nuolaida negali pasibaigti anksčiau, nei prasidėti"));
        }
    }
}
