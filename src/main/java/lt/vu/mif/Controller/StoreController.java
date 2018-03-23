package lt.vu.mif.Controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class StoreController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView mainWindow() {

        Map<String, Object> params = new HashMap<>();
        params.put("intro", "Welcome to our store!");
        return new ModelAndView("window", params);
    }

    @RequestMapping(value = "/product-details", method = RequestMethod.GET)
    public ModelAndView productDetailsWindow() {
        return new ModelAndView("product-details");
    }

    @RequestMapping(value = "/logged-in", method = RequestMethod.GET)
    public ModelAndView loggedIn() { return new ModelAndView("main-page-logged-in.html"); }

    @RequestMapping(value = "/testrequest", method = RequestMethod.GET)
    @ResponseBody
    public String[] testRequest() {
        return new String[]{
                "123",
                "123456",
                "456"
        };
    }
}
