package lt.vu.mif.Controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Named;
import lt.vu.mif.View.ImageView;
import lt.vu.mif.View.ProductView;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Named
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

    public ProductView[] getProducts(){
        return new ProductView[] {
            new ProductView(1l, "sometitle1", new BigDecimal(1256), "This is a description", new ArrayList<ImageView>()),
            new ProductView(1l, "sometitle1", new BigDecimal(1256), "This is a description", new ArrayList<ImageView>()),
            new ProductView(1l, "sometitle1", new BigDecimal(1256), "This is a description", new ArrayList<ImageView>()),
            new ProductView(1l, "sometitle1", new BigDecimal(1256), "This is a description", new ArrayList<ImageView>()),
            new ProductView(1l, "sometitle1", new BigDecimal(1256), "This is a description", new ArrayList<ImageView>()),
            new ProductView(1l, "sometitle1", new BigDecimal(1256), "This is a description", new ArrayList<ImageView>()),
            new ProductView(1l, "sometitle1", new BigDecimal(1256), "This is a description", new ArrayList<ImageView>()),
            new ProductView(1l, "sometitle1", new BigDecimal(1256), "This is a description", new ArrayList<ImageView>()),
            new ProductView(1l, "sometitle1", new BigDecimal(1256), "This is a description", new ArrayList<ImageView>()),
            new ProductView(1l, "sometitle1", new BigDecimal(1256), "This is a description", new ArrayList<ImageView>()),
            new ProductView(1l, "sometitle1", new BigDecimal(1256), "This is a description", new ArrayList<ImageView>()),
            new ProductView(1l, "sometitle1", new BigDecimal(1256), "This is a description", new ArrayList<ImageView>()),
            new ProductView(1l, "sometitle1", new BigDecimal(1256), "This is a description", new ArrayList<ImageView>()),
            new ProductView(1l, "sometitle1", new BigDecimal(1256), "This is a description", new ArrayList<ImageView>()),
        };
    }
}
