package lt.vu.mif.ui.validation;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import lt.vu.mif.ui.view.AttributeValue;
import lt.vu.mif.ui.view.AttributeView;
import lt.vu.mif.ui.view.CategoryView;
import org.apache.commons.lang3.StringUtils;

@Named
public class CategoryValidation implements Validator {

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o)
        throws ValidatorException {
        List<CategoryView> categories = (List<CategoryView>) uiComponent.getAttributes()
            .get("categories");

        for (CategoryView c : categories) {
            for (AttributeView a : c.getAttributes()) {
                if (StringUtils.isEmpty(a.getName())) {
                    throw new ValidatorException(new FacesMessage(
                        "Kategorijoje " + c.getName() + " ne visi požymių vardai užpildyti"));
                }

                if (a.getValues().isEmpty()) {
                    throw new ValidatorException(new FacesMessage(
                        "Kategorijoje " + c.getName() + " požymyje " + a.getName()
                            + " nėra nei vienos reikšmės"));
                }

                for (AttributeValue v : a.getValues()) {
                    if (StringUtils.isEmpty(v.getValue())) {
                        throw new ValidatorException(new FacesMessage(
                            "Kategorijoje " + c.getName() + " požymyje " + a.getName()
                                + " ne visos požymių reikšmės užpildytos"));
                    }
                }
            }
        }
    }
}
