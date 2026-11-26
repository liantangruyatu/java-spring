package com.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StaticResourceController {
    
    @RequestMapping(value = "/")
    public String index() {
        return "forward:/index.html";
    }
    
    @RequestMapping(value = "/static/**")
    public String staticResource() {
        return "forward:/index.html";
    }
}