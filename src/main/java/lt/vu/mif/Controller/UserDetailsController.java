package lt.vu.mif.Controller;

import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.Entity.User;
import lt.vu.mif.Repository.UserRepository;
import lt.vu.mif.View.UserView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.faces.context.FacesContext;
import javax.inject.Named;

@Getter
@Setter
@Named
public class UserDetailsController {
    @Autowired
    private UserRepository userRepository;
    private UserView userView;

    public void onPageLoad() {
        String userId  = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("userId");
        if (StringUtils.isBlank(userId)) {
            throw new IllegalArgumentException("Invalid request parameter");
        }

        User entity = userRepository.get(Long.valueOf(userId));

        if (entity == null) {
            throw new IllegalStateException("User" + "with ID=" + userId +  "not found");
        }

        userView = new UserView(entity);
    }
}