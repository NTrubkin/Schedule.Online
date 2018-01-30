package com.company.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/")
public class PageController {
    @RequestMapping(value = "", method = RequestMethod.GET)
    private String getIndexPage() {
        return "index";
    }
}
