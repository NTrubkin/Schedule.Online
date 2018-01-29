package com.company.controller.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class Controller {

    // todo найти способ внедрить дао через конфиг
    @RequestMapping(value = "/", method = RequestMethod.GET)
    private ResponseEntity getResponse() {
        return new ResponseEntity(HttpStatus.OK);
    }
}
