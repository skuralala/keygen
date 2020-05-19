package com.cxm.resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewResource {

    @RequestMapping("/main")
    public String index() {
        return "main";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }
}
