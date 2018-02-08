package com.company.controller.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/account")
public class AccountRestController {
    // accountId == 0 это аутентифицированный аккаунт
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity getAccount() {

        return new ResponseEntity(HttpStatus.OK);
    }
}
