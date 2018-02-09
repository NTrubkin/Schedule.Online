package com.company.controller.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/comb/")
public class CombineRestController {

    /**
     * Объединяет rest-запросы, вызываемые на главной странице, в один (для ускорения)
     *
     * Включает GET-запросы:
     * /api/account
     * /api/group
     * /api/group/{groupId}/lessons
     * /api/group/{groupId}/events
     * @return
     */
    @RequestMapping(value = "/mainpage", method = RequestMethod.GET)
    private ResponseEntity getMainPageData(@RequestParam(required = false) boolean lessons,
                                           @RequestParam(required = false) boolean events ) {
        return new ResponseEntity(HttpStatus.OK);
    }
}
