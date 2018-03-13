package com.gazebo.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class StoreController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView mainWindow() {

        Map<String, Object> params = new HashMap<>();
        params.put("intro", "Welcome to our store!");
        return new ModelAndView("window", params);
    }
}
