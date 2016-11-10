package guru.springframework.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping({"/", ""})
    public String index() {
        return "index";
    }

    @RequestMapping("/login")
    public String loginForm() {
        return "login";
    }

    @RequestMapping("/access_denied")
    public String notAuth() {
        return "access_denied";
    }
}
