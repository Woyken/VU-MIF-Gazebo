package com.gazebo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoApiController {
    @RequestMapping("/test")
    public String home() {
        return "Test.";
    }
}
