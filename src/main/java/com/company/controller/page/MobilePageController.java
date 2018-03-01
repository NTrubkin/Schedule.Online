package com.company.controller.page;

import com.company.dao.api.AccountDAO;
import com.company.dao.api.EventDAO;
import com.company.dao.api.LessonDAO;
import com.company.dao.api.PermissionDAO;
import com.company.dto.*;
import com.company.dto.converter.EntityConverter;
import com.company.model.*;
import com.company.service.auth.CustomUserDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/m/")
public class MobilePageController {
    private final AccountDAO accountDAO;
    private final LessonDAO lessonDAO;
    private final EventDAO eventDAO;
    private final PermissionDAO permissionDAO;
    private final EntityConverter<Account, PrivateAccountDTO> privateAccConverter;
    private final EntityConverter<Account, AccountDTO> accConverter;
    private final EntityConverter<Group, GroupDTO> groupConverter;
    private final EntityConverter<Lesson, LessonDTO> lessonConverter;
    private final EntityConverter<Event, EventDTO> eventConverter;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    public MobilePageController(AccountDAO accountDAO, LessonDAO lessonDAO, EventDAO eventDAO, PermissionDAO permissionDAO, EntityConverter<Account, PrivateAccountDTO> privateAccConverter, EntityConverter<Account, AccountDTO> accConverter, EntityConverter<Group, GroupDTO> groupConverter, EntityConverter<Lesson, LessonDTO> lessonConverter, EntityConverter<Event, EventDTO> eventConverter) {
        this.accountDAO = accountDAO;
        this.lessonDAO = lessonDAO;
        this.eventDAO = eventDAO;
        this.permissionDAO = permissionDAO;
        this.privateAccConverter = privateAccConverter;
        this.accConverter = accConverter;
        this.groupConverter = groupConverter;
        this.lessonConverter = lessonConverter;
        this.eventConverter = eventConverter;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getIndexPage(Authentication auth, Model model) throws JsonProcessingException {
        Account account = readAndInjectHeaderAttributes(auth, model);
        Permission permission = permissionDAO.readByAccount(account.getId());
        if(permission != null) {
            model.addAttribute("canEditLessons", permission.getLessonsEdit());
            model.addAttribute("canEditEvents", permission.getEventsEdit());
        }
        else {
            model.addAttribute("canEditLessons", false);
            model.addAttribute("canEditEvents", false);
        }
        return "mobile/index";
    }

    private Account readAndInjectHeaderAttributes(Authentication auth, Model model) throws JsonProcessingException {
        if (!(auth.getPrincipal() instanceof CustomUserDetails)) {
            throw new IllegalArgumentException("Authentication principal should implement " + CustomUserDetails.class.getName());
        }

        int accountId = ((CustomUserDetails) auth.getPrincipal()).getUserId();
        Account account = accountDAO.read(accountId);
        PrivateAccountDTO accountDTO = privateAccConverter.convert(account);
        GroupDTO groupDTO = groupConverter.convert(account.getGroup());
        model.addAttribute("accountDTO", MAPPER.writeValueAsString(accountDTO));
        model.addAttribute("groupDTO", MAPPER.writeValueAsString(groupDTO));

        return account;
    }
}
