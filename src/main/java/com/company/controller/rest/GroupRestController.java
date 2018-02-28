package com.company.controller.rest;

import com.company.dao.api.AccountDAO;
import com.company.dao.api.GroupDAO;
import com.company.dao.api.PermissionDAO;
import com.company.dto.*;
import com.company.dto.converter.IEntityConverter;
import com.company.model.Account;
import com.company.model.Group;
import com.company.model.Permission;
import com.company.service.auth.CustomUserDetails;
import com.company.service.sender.NotificationService;
import com.company.util.LoginValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/group")
public class GroupRestController {

    private final AccountDAO accountDAO;
    private final GroupDAO groupDAO;
    private final PermissionDAO permissionDAO;
    private final IEntityConverter<Group, GroupDTO> groupConverter;
    private final NotificationService notificationService;
    private final LessonRestController lessonController;
    private final EventRestController eventController;

    @Autowired
    public GroupRestController(AccountDAO accountDAO, GroupDAO groupDAO, PermissionDAO permissionDAO, IEntityConverter<Group, GroupDTO> groupConverter, NotificationService notificationService, LessonRestController lessonController, EventRestController eventController) {
        this.accountDAO = accountDAO;
        this.groupDAO = groupDAO;
        this.permissionDAO = permissionDAO;
        this.groupConverter = groupConverter;
        this.notificationService = notificationService;
        this.lessonController = lessonController;
        this.eventController = eventController;
    }

    @RequestMapping(value = "/{groupId}", method = RequestMethod.GET)
    public ResponseEntity<GroupDTO> readGroup(Authentication auth, @PathVariable int groupId) {
        Group group = groupDAO.read(groupId);
        GroupDTO groupDTO = groupConverter.convert(group);
        return new ResponseEntity<>(groupDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity createGroup(@RequestBody GroupDTO groupDTO, Authentication auth) {
        if (!(auth.getPrincipal() instanceof CustomUserDetails)) {
            throw new IllegalArgumentException("Authentication principal should implement " + CustomUserDetails.class);
        }

        int accountId = ((CustomUserDetails)auth.getPrincipal()).getUserId();
        groupDTO.setLeaderId(accountId);
        Group group = groupConverter.restore(groupDTO);
        groupDAO.create(group);
        Account account = accountDAO.read(accountId);
        account.setGroup(group);
        accountDAO.update(account);
        Permission permission = new Permission();
        permission.setAccountId(account.getId());
        permission.setGroupId(group.getId());
        permission.setAdmin(true);
        permission.setEventsEdit(true);
        permission.setLessonsEdit(true);
        permissionDAO.create(permission);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity updateGroup(@RequestBody GroupDTO groupDTO, @RequestParam(name = "notify", defaultValue = "true") boolean notify) {
        Group group = groupConverter.restore(groupDTO);
        groupDAO.update(group);
        if (notify) {
            notificationService.sendSettingsNotifications(group.getId());
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/{groupId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteGroup(@PathVariable int groupId, @RequestParam(name = "notify", defaultValue = "true") boolean notify) {
        // уведомление должно быть раньше удаления (иначе некого уведомлять)
        if (notify) {
            notificationService.sendSettingsNotifications(groupId);
        }
        groupDAO.delete(groupId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/permission", method = RequestMethod.PUT)
    public ResponseEntity updatePermissions(@RequestBody List<Permission> permissions, @RequestParam(name = "notify", defaultValue = "true") boolean notify) {
        permissionDAO.update(permissions);
        if (notify) {
            notificationService.sendSettingsNotifications(permissions.get(0).getGroupId());
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/member/invite", method = RequestMethod.POST)
    public ResponseEntity addMember(@RequestParam(name = "login") String login, Authentication auth, @RequestParam(name = "notify", defaultValue = "true") boolean notify) {
        if (!(auth.getPrincipal() instanceof CustomUserDetails)) {
            throw new IllegalArgumentException("Authentication principal should implement " + CustomUserDetails.class);
        }

        int accountId = ((CustomUserDetails)auth.getPrincipal()).getUserId();
        Group group = accountDAO.read(accountId).getGroup();
        Account newMember;
        if(LoginValidator.isEmailValid(login)) {
            newMember = accountDAO.readByEmail(login);
            newMember.setGroup(group);
            accountDAO.update(newMember);
        }
        else if(LoginValidator.isPhoneNumberValid(login)) {
            newMember = accountDAO.readByPhoneNumber(new Long(login));
            newMember.setGroup(group);
            accountDAO.update(newMember);

        }
        else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Permission permission = new Permission();
        permission.setAccountId(newMember.getId());
        permission.setGroupId(group.getId());
        permission.setAdmin(false);
        permission.setEventsEdit(false);
        permission.setLessonsEdit(false);
        permissionDAO.create(permission);
        if(notify) {
            notificationService.sendSettingsNotifications(group.getId());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/member", method = RequestMethod.DELETE)
    public ResponseEntity deleteMembers(@RequestBody List<Integer> memberIds, @RequestParam(name = "notify", defaultValue = "true") boolean notify) {
        int groupId = 0;
        for (Integer memberId : memberIds) {
            Account account = accountDAO.read(memberId);
            groupId = account.getGroup().getId();
            account.setGroup(null);
            accountDAO.update(account);
            permissionDAO.delete(permissionDAO.readByAccount(memberId));
        }
        if (notify) {
            notificationService.sendSettingsNotifications(groupId);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/lessonsAndEvents", method = RequestMethod.GET)
    public ResponseEntity<LessonsAndEventsDTO> getLessonsAndEvents(Authentication auth) {
        if (!(auth.getPrincipal() instanceof CustomUserDetails)) {
            throw new IllegalArgumentException("Authentication principal should implement " + CustomUserDetails.class);
        }

        int accountId = ((CustomUserDetails)auth.getPrincipal()).getUserId();
        int groupId = accountDAO.read(accountId).getGroup().getId();

        List<LessonDTO> lessons = lessonController.readLessonsByGroup(groupId).getBody();
        List<EventDTO> events = eventController.readEventsByGroup(groupId).getBody();

        return new ResponseEntity<>(new LessonsAndEventsDTO(lessons, events), HttpStatus.OK);
    }
}
