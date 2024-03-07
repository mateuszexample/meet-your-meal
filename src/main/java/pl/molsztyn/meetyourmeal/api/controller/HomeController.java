package pl.molsztyn.meetyourmeal.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

    static final String HOME = "/";
    @RequestMapping(value = HOME, method = RequestMethod.GET)
    public String home() {
        return "home";
    }
}
