package com.company.controller.page;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/")
public class PageController {
    @RequestMapping(value = "", method = RequestMethod.GET)
    private String getIndexPage(Authentication auth) {
        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    private String getLoginPage(@RequestParam(name = "login_error", required = false, defaultValue = "0") int loginError, Model model) {
        if (loginError != 0) {
            model.addAttribute("message", "Login error");
        }
        return "login";
    }

    @RequestMapping(value = "/cookie", method = RequestMethod.GET)
    private String getCookiePage() {
        return "cookie";
    }
}
