package com.company.controller.page;

import com.company.dao.api.AccountDAO;
import com.company.dao.api.EventDAO;
import com.company.dao.api.LessonDAO;
import com.company.dao.api.PermissionDAO;
import com.company.dto.*;
import com.company.dto.converter.EntityConverter;
import com.company.model.Account;
import com.company.model.Event;
import com.company.model.Group;
import com.company.model.Lesson;
import com.company.service.auth.CustomUserDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(value = "/")
public class PageController {

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
    public PageController(AccountDAO accountDAO, LessonDAO lessonDAO, EventDAO eventDAO, PermissionDAO permissionDAO, EntityConverter<Account, PrivateAccountDTO> privateAccConverter, EntityConverter<Account, AccountDTO> accConverter, EntityConverter<Group, GroupDTO> groupConverter, EntityConverter<Lesson, LessonDTO> lessonConverter, EntityConverter<Event, EventDTO> eventConverter) {
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

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getIndexPage(Authentication auth, Model model) throws JsonProcessingException {
        readAndInjectHeaderAttributes(auth, model);
        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLoginPage(@RequestParam(name = "login_error", required = false, defaultValue = "0") int loginError, Model model, Authentication auth) {
        if (loginError != 0) {
            model.addAttribute("message", "Login error");
        }
        return "login";
    }

    @RequestMapping(value = "/new-lesson")
    public String getNewLessonPage(Authentication auth, Model model) throws JsonProcessingException {
        readAndInjectHeaderAttributes(auth, model);
        return "newLesson";
    }

    @RequestMapping(value = "/lesson")
    public String getLessonPage(@RequestParam(name = "id", defaultValue = "0") int lessonId, Authentication auth, Model model) throws JsonProcessingException {
        readAndInjectHeaderAttributes(auth, model);
        Lesson lesson = lessonDAO.read(lessonId);
        LessonDTO lessonDTO = lessonConverter.convert(lesson);
        model.addAttribute("lessonDTO", MAPPER.writeValueAsString(lessonDTO));
        return "lesson";
    }

    @RequestMapping(value = "/event")
    public String getEventPage(@RequestParam(name = "id", defaultValue = "0") int eventId, Authentication auth, Model model) throws JsonProcessingException {
        readAndInjectHeaderAttributes(auth, model);
        Event event = eventDAO.read(eventId);
        EventDTO eventDTO = eventConverter.convert(event);
        model.addAttribute("eventDTO", MAPPER.writeValueAsString(eventDTO));
        return "event";
    }

    @RequestMapping(value = "/new-event")
    public String getNewEventPage(Authentication auth, Model model) throws JsonProcessingException {
        readAndInjectHeaderAttributes(auth, model);
        return "newEvent";
    }

    @RequestMapping(value = "/account")
    public String getAccountPage(Authentication auth, Model model) throws JsonProcessingException {
        readAndInjectHeaderAttributes(auth, model);
        return "account";
    }


    @RequestMapping(value = "/group")
    public String getGroupPage(Authentication auth, Model model) throws JsonProcessingException {
        Account account = readAndInjectHeaderAttributes(auth, model);
        int groupId = account.getGroup().getId();

        List<AccountDTO> membersDTO = accConverter.convert(accountDAO.readByGroup(groupId));

        model.addAttribute("membersDTO", MAPPER.writeValueAsString(membersDTO));
        model.addAttribute("permissionsDTO", MAPPER.writeValueAsString(permissionDAO.readByGroup(groupId)));
        return "group";
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
