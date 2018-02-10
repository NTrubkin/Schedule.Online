package com.company.controller.rest;

import com.company.dto.GroupDTO;
import com.company.dto.LessonDTO;
import com.company.dto.MainPageDTO;
import com.company.dto.PrivateAccountDTO;
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

@RestController
@RequestMapping(value = "/api/comb/")
public class CombineRestController {

    private final AccountRestController accountController;
    private final GroupRestController groupController;
    private final LessonRestController lessonController;

    @Autowired
    public CombineRestController(AccountRestController accountController, GroupRestController groupController, LessonRestController lessonController) {
        this.accountController = accountController;
        this.groupController = groupController;
        this.lessonController = lessonController;
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
        GroupDTO groupDTO = groupController.readGroup(auth, accountDTO.getGroup_id()).getBody();
        List<LessonDTO> lessons = lessonController.readLessonsByGroup(groupDTO.getId()).getBody();
        MainPageDTO result = new MainPageDTO(accountDTO, groupDTO, lessons, null);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
