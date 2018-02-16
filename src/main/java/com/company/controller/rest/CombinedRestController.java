package com.company.controller.rest;

import com.company.dto.*;
import com.company.model.Account;
import com.company.model.Lesson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// todo упразднить инициализирующие комбинированные rest-запросы, вынести в jsp
@RestController
@RequestMapping(value = "/api/comb/")
public class CombinedRestController {

    private final AccountRestController accountController;
    private final GroupRestController groupController;
    private final LessonRestController lessonController;
    private final EventRestController eventController;

    @Autowired
    public CombinedRestController(AccountRestController accountController, GroupRestController groupController, LessonRestController lessonController, EventRestController eventController) {
        this.accountController = accountController;
        this.groupController = groupController;
        this.lessonController = lessonController;
        this.eventController = eventController;
    }

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
    private ResponseEntity<MainPageDTO> getMainPageData(Authentication auth) {
        PrivateAccountDTO accountDTO = accountController.readAccount(auth).getBody();
        GroupDTO groupDTO = null;
        List<LessonDTO> lessons = null;
        List<EventDTO> events = null;

        if(accountDTO.getGroupId() != null) {
            groupDTO = groupController.readGroup(auth, accountDTO.getGroupId()).getBody();
            lessons = lessonController.readLessonsByGroup(groupDTO.getId()).getBody();
            events = eventController.readEventsByGroup(groupDTO.getId()).getBody();
        }

        MainPageDTO result = new MainPageDTO(accountDTO, groupDTO, lessons, events);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
