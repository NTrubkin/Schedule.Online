package com.company.controller.rest;

import com.company.dao.api.AccountDAO;
import com.company.dao.api.EventDAO;
import com.company.dao.api.PermissionDAO;
import com.company.dto.ErrorMessageDTO;
import com.company.dto.EventDTO;
import com.company.dto.converter.IEntityConverter;
import com.company.model.Event;
import com.company.model.Group;
import com.company.model.Permission;
import com.company.service.auth.CustomUserDetails;
import com.company.service.sender.NotificationService;
import com.company.util.CommonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/event")
public class EventRestController {

    private static final String NOT_IN_GROUP_MSG = "Вы не состоите в этой группе";
    private static final String NOT_HAVE_PERMS_MSG = "Вы не имеете права для редактирования событий";
    private static final String NOT_FOUND_MSG = "событие не найдено";
    private static final String INVALID_NAME_MSG = "неверное имя события";
    private static final String INVALID_PLACE_MSG = "неверное место события";
    private static final String INVALID_DESC_MSG = "неверное описание события";
    private static final String INVALID_START_MSG = "неверное время начала занятия";
    private static final String AUTH_PRINCIPAL_MSG = "Authentication principal should implement ";

    private final AccountDAO accountDAO;
    private final EventDAO eventDAO;
    private final PermissionDAO permissionDAO;
    private final IEntityConverter<Event, EventDTO> converter;
    private final NotificationService notificationService;

    @Autowired
    public EventRestController(AccountDAO accountDAO, EventDAO eventDAO, PermissionDAO permissionDAO, IEntityConverter<Event, EventDTO> converter, NotificationService notificationService) {
        this.accountDAO = accountDAO;
        this.eventDAO = eventDAO;
        this.permissionDAO = permissionDAO;
        this.converter = converter;
        this.notificationService = notificationService;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity createEvent(@RequestBody EventDTO eventDTO, @RequestParam(name = "notify", defaultValue = "true") boolean notify, Authentication auth) {
        if (!(auth.getPrincipal() instanceof CustomUserDetails)) {
            throw new IllegalArgumentException(AUTH_PRINCIPAL_MSG + CustomUserDetails.class);
        }
        int accountId = ((CustomUserDetails) auth.getPrincipal()).getUserId();
        Group group = accountDAO.read(accountId).getGroup();

        ErrorMessageDTO errorDTO = new ErrorMessageDTO();

        if (!checkEventGroupAndPerms(group, eventDTO, accountId, errorDTO)) {
            return new ResponseEntity(errorDTO, HttpStatus.BAD_REQUEST);
        }

        checkEventFields(eventDTO, errorDTO);
        if (errorDTO.getMessages() == null) {
            eventDAO.create(converter.restore(eventDTO));
            if (notify) {
                notificationService.sendScheduleNotifications(eventDTO.getGroupId());
            }
            return new ResponseEntity(HttpStatus.OK);
        }
        else {
            return new ResponseEntity(errorDTO, HttpStatus.BAD_REQUEST);
        }
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/{eventId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteEvent(@PathVariable int eventId, @RequestParam(name = "notify", defaultValue = "true") boolean notify, Authentication auth) {
        if (!(auth.getPrincipal() instanceof CustomUserDetails)) {
            throw new IllegalArgumentException(AUTH_PRINCIPAL_MSG + CustomUserDetails.class);
        }
        int accountId = ((CustomUserDetails) auth.getPrincipal()).getUserId();
        Group group = accountDAO.read(accountId).getGroup();
        ErrorMessageDTO errorDTO = new ErrorMessageDTO();
        if (group == null) {
            errorDTO.addMessage(NOT_IN_GROUP_MSG);
            return new ResponseEntity(errorDTO, HttpStatus.BAD_REQUEST);
        }

        Event event = eventDAO.read(eventId);
        if (event == null) {
            errorDTO.addMessage(NOT_FOUND_MSG);
            return new ResponseEntity(errorDTO, HttpStatus.BAD_REQUEST);
        }

        if (!event.getGroup().getId().equals(group.getId())) {
            errorDTO.addMessage(NOT_IN_GROUP_MSG);
            return new ResponseEntity(errorDTO, HttpStatus.BAD_REQUEST);
        }

        Permission permission = permissionDAO.readByAccount(accountId);
        if(!permission.getEventsEdit()) {
            errorDTO.addMessage(NOT_HAVE_PERMS_MSG);
            return new ResponseEntity(errorDTO, HttpStatus.BAD_REQUEST);
        }

        if (notify) {
            notificationService.sendScheduleNotifications(event.getGroup().getId());
        }
        eventDAO.delete(event);
        return new ResponseEntity(HttpStatus.OK);
    }

    ResponseEntity<List<EventDTO>> readEventsByGroup(int groupId) {
        List<Event> events = eventDAO.readAllByGroup(groupId);
        return new ResponseEntity<>(converter.convert(events), HttpStatus.OK);
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity updateEvent(@RequestBody EventDTO eventDTO, @RequestParam(name = "notify", defaultValue = "true") boolean notify, Authentication auth) {
        if (!(auth.getPrincipal() instanceof CustomUserDetails)) {
            throw new IllegalArgumentException(AUTH_PRINCIPAL_MSG + CustomUserDetails.class);
        }
        int accountId = ((CustomUserDetails) auth.getPrincipal()).getUserId();
        Group group = accountDAO.read(accountId).getGroup();

        ErrorMessageDTO errorDTO = new ErrorMessageDTO();

        if (!checkEventGroupAndPerms(group, eventDTO, accountId, errorDTO)) {
            return new ResponseEntity(errorDTO, HttpStatus.BAD_REQUEST);
        }

        if(eventDAO.read(eventDTO.getId()) == null) {
            errorDTO.addMessage(NOT_FOUND_MSG);
            return new ResponseEntity(errorDTO, HttpStatus.BAD_REQUEST);
        }

        checkEventFields(eventDTO, errorDTO);
        if (errorDTO.getMessages() == null) {
            Event event = converter.restore(eventDTO);
            eventDAO.update(event);
            if (notify) {
                notificationService.sendScheduleNotifications(eventDTO.getGroupId());
            }
            return new ResponseEntity(HttpStatus.OK);
        }
        else {
            return new ResponseEntity(errorDTO, HttpStatus.BAD_REQUEST);
        }
    }

    private void checkEventFields(EventDTO eventDTO, ErrorMessageDTO errorDTO) {
        if (!CommonValidator.isNameValid(eventDTO.getName())) {
            errorDTO.addMessage(INVALID_NAME_MSG);
        }

        if (!CommonValidator.isTextValid(eventDTO.getDescription())) {
            errorDTO.addMessage(INVALID_DESC_MSG);
        }

        if (!CommonValidator.isNameValid(eventDTO.getPlace())) {
            errorDTO.addMessage(INVALID_PLACE_MSG);
        }

        if (eventDTO.getStartDatetime() == null) {
            errorDTO.addMessage(INVALID_START_MSG);
        }
    }

    private boolean checkEventGroupAndPerms(Group group, EventDTO eventDTO, int accountId, ErrorMessageDTO errorDTO) {
        if (group == null || group.getId().intValue() != eventDTO.getGroupId()) {
            errorDTO.addMessage(NOT_IN_GROUP_MSG);
            return false;
        }
        else {
            Permission permission = permissionDAO.readByAccount(accountId);
            if (!permission.getEventsEdit()) {
                errorDTO.addMessage(NOT_HAVE_PERMS_MSG);
                return false;
            }
        }
        return true;
    }
}
