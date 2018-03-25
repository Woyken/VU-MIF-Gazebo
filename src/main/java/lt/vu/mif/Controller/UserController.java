package lt.vu.mif.Controller;

import javax.inject.Named;

import lt.vu.mif.Entity.Roles.Role;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import lombok.Getter;
import lt.vu.mif.View.UserView;

@Named
@Getter
@Controller
public class UserController {
    private UserView userView = new UserView(10251L, "test@gmail.com", Role.USER);

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ModelAndView getUserWindow() {
        return new ModelAndView("user.xhtml");
    }
}
