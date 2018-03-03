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
import com.company.util.CommonValidator;
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

    private static final String NOT_FOUND_MSG = "группа не найдена";
    private static final String LEADER_DEL_MSG = "попытка удалить лидера";
    private static final String ADMIN_REQ_MSG = "требуются права администратора";
    private static final String LEADER_REQ_MSG = "требуются права лидера";
    private static final String ACC_NOT_FOUND_MSG = "аккаунт не найден";
    private static final String ALREADY_IN_GROUP_MSG = "аккаунт уже состоит в группе";
    private static final String NOT_IN_GROUP_MSG = "аккаунт не состоит в группе";
    private static final String LEADER_NOT_IN_GROUP_MSG = "лидер не состоит в группе";
    private static final String INVALID_GROUP_NAME_MSG = "неверное имя группы";
    private static final String AUTH_PRINCIPAL_MSG = "Authentication principal should implement ";

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

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity createGroup(@RequestBody GroupDTO groupDTO, Authentication auth) {
        if (!(auth.getPrincipal() instanceof CustomUserDetails)) {
            throw new IllegalArgumentException(AUTH_PRINCIPAL_MSG + CustomUserDetails.class);
        }

        int accountId = ((CustomUserDetails) auth.getPrincipal()).getUserId();
        Account account = accountDAO.read(accountId);
        if(account.getGroup() != null) {
            return new ResponseEntity(new ErrorDTO(ALREADY_IN_GROUP_MSG), HttpStatus.BAD_REQUEST);
        }

        ErrorDTO errorDTO = new ErrorDTO();
        if(!checkGroup(groupDTO, errorDTO)) {
            return new ResponseEntity(errorDTO, HttpStatus.BAD_REQUEST);
        }

        groupDTO.setLeaderId(accountId);
        Group group = groupConverter.restore(groupDTO);
        groupDAO.create(group);
        account.setGroup(group);
        accountDAO.update(account);
        createPermission(accountId, group.getId(), true, true, true);
        return new ResponseEntity(HttpStatus.OK);
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity updateGroup(@RequestBody GroupDTO groupDTO, @RequestParam(name = "notify", defaultValue = "true") boolean notify, Authentication auth) {
        if (!(auth.getPrincipal() instanceof CustomUserDetails)) {
            throw new IllegalArgumentException(AUTH_PRINCIPAL_MSG + CustomUserDetails.class);
        }

        int accountId = ((CustomUserDetails) auth.getPrincipal()).getUserId();
        Account account = accountDAO.read(accountId);
        Group group = account.getGroup();

        if (group == null) {
            return new ResponseEntity(new ErrorDTO(NOT_FOUND_MSG), HttpStatus.BAD_REQUEST);
        }

        if (!account.getGroup().getId().equals(groupDTO.getId())) {
            return new ResponseEntity(new ErrorDTO(NOT_IN_GROUP_MSG), HttpStatus.BAD_REQUEST);
        }

        Permission permission = permissionDAO.readByAccount(accountId);
        if(!permission.getAdmin()) {
            return new ResponseEntity(new ErrorDTO(ADMIN_REQ_MSG), HttpStatus.BAD_REQUEST);
        }

        ErrorDTO errorDTO = new ErrorDTO();
        if(!checkGroup(groupDTO, errorDTO)) {
            return new ResponseEntity(errorDTO, HttpStatus.BAD_REQUEST);
        }

        if(!group.getLeader().getId().equals(groupDTO.getLeaderId())) {
            if(!group.getLeader().getId().equals(accountId)) {
                return new ResponseEntity(new ErrorDTO(LEADER_REQ_MSG), HttpStatus.BAD_REQUEST);
            }
            else {
                Permission newAdminPerm = permissionDAO.readByAccount(groupDTO.getLeaderId());
                newAdminPerm.setAdmin(true);
                newAdminPerm.setLessonsEdit(true);
                newAdminPerm.setEventsEdit(true);
                permissionDAO.update(permission);
            }
        }

        Group newGroup = groupConverter.restore(groupDTO);
        groupDAO.update(newGroup);
        if (notify) {
            notificationService.sendSettingsNotifications(newGroup.getId());
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/permission", method = RequestMethod.PUT)
    public ResponseEntity updatePermissions(@RequestBody List<Permission> permissions, @RequestParam(name = "notify", defaultValue = "true") boolean notify, Authentication auth) {
        if (!(auth.getPrincipal() instanceof CustomUserDetails)) {
            throw new IllegalArgumentException(AUTH_PRINCIPAL_MSG + CustomUserDetails.class);
        }

        int accountId = ((CustomUserDetails) auth.getPrincipal()).getUserId();
        Account account = accountDAO.read(accountId);
        Group group = account.getGroup();

        if (group == null) {
            return new ResponseEntity(new ErrorDTO(NOT_FOUND_MSG), HttpStatus.BAD_REQUEST);
        }

        Permission permission = permissionDAO.readByAccount(accountId);
        if(!permission.getAdmin()) {
            return new ResponseEntity(new ErrorDTO(ADMIN_REQ_MSG), HttpStatus.BAD_REQUEST);
        }

        ErrorDTO errorDTO = new ErrorDTO();
        permissions.forEach(x -> {
            if (group.getLeader().getId().equals(x.getAccountId()) && !group.getLeader().equals(accountId)) {
                if(!x.getAdmin() || !x.getLessonsEdit() || !x.getEventsEdit()) {
                    errorDTO.addMessage(LEADER_REQ_MSG);
                }
            }
        });
        if (!errorDTO.getMessages().isEmpty()) {
            return new ResponseEntity(errorDTO, HttpStatus.BAD_REQUEST);
        }

        permissionDAO.update(permissions);
        if (notify) {
            notificationService.sendSettingsNotifications(permissions.get(0).getGroupId());
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/member/invite", method = RequestMethod.POST)
    public ResponseEntity addMember(@RequestParam(name = "login") String login, Authentication auth, @RequestParam(name = "notify", defaultValue = "true") boolean notify) {
        if (!(auth.getPrincipal() instanceof CustomUserDetails)) {
            throw new IllegalArgumentException(AUTH_PRINCIPAL_MSG + CustomUserDetails.class);
        }

        int accountId = ((CustomUserDetails) auth.getPrincipal()).getUserId();
        Account account = accountDAO.read(accountId);
        Group group = account.getGroup();

        if (group == null) {
            return new ResponseEntity(new ErrorDTO(NOT_FOUND_MSG), HttpStatus.BAD_REQUEST);
        }

        Permission permission = permissionDAO.readByAccount(accountId);
        if(!permission.getAdmin()) {
            return new ResponseEntity(new ErrorDTO(ADMIN_REQ_MSG), HttpStatus.BAD_REQUEST);
        }


        Account newMember = null;
        if (LoginValidator.isEmailValid(login)) {
            newMember = accountDAO.readByEmail(login);

        }
        else if (LoginValidator.isPhoneNumberValid(login)) {
            newMember = accountDAO.readByPhoneNumber(new Long(login));

        }

        if(newMember == null) {
            return new ResponseEntity<>(new ErrorDTO(ACC_NOT_FOUND_MSG), HttpStatus.BAD_REQUEST);
        }

        if (newMember.getGroup() != null) {
            return new ResponseEntity<>(new ErrorDTO(ALREADY_IN_GROUP_MSG), HttpStatus.BAD_REQUEST);
        }

        newMember.setGroup(group);
        accountDAO.update(newMember);

        createPermission(newMember.getId(), group.getId(), false, false, false);
        if (notify) {
            notificationService.sendSettingsNotifications(group.getId());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/member", method = RequestMethod.DELETE)
    public ResponseEntity deleteMembers(@RequestBody List<Integer> memberIds, @RequestParam(name = "notify", defaultValue = "true") boolean notify, Authentication auth) {
        if (!(auth.getPrincipal() instanceof CustomUserDetails)) {
            throw new IllegalArgumentException(AUTH_PRINCIPAL_MSG + CustomUserDetails.class);
        }

        int accountId = ((CustomUserDetails) auth.getPrincipal()).getUserId();
        Account account = accountDAO.read(accountId);
        Group group = account.getGroup();

        if (group == null) {
            return new ResponseEntity(new ErrorDTO(NOT_FOUND_MSG), HttpStatus.BAD_REQUEST);
        }

        Permission permission = permissionDAO.readByAccount(accountId);
        if(!permission.getAdmin()) {
            return new ResponseEntity(new ErrorDTO(ADMIN_REQ_MSG), HttpStatus.BAD_REQUEST);
        }

        ErrorDTO errorDTO = new ErrorDTO();
        memberIds.forEach(x -> {
            if (x.equals(group.getLeader().getId())) {
                errorDTO.addMessage(LEADER_DEL_MSG);
            }
        });

        if (!errorDTO.getMessages().isEmpty()) {
            return new ResponseEntity(errorDTO, HttpStatus.BAD_REQUEST);
        }

        for (Integer memberId : memberIds) {
            Account member = accountDAO.read(memberId);
            int groupId = member.getGroup().getId();
            if(!group.getId().equals(groupId))    {
                return new ResponseEntity(NOT_IN_GROUP_MSG, HttpStatus.BAD_REQUEST);
            }
            member.setGroup(null);
            accountDAO.update(member);
            permissionDAO.delete(permissionDAO.readByAccount(memberId));
        }
        if (notify) {
            notificationService.sendSettingsNotifications(group.getId());
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/lessonsAndEvents", method = RequestMethod.GET)
    public ResponseEntity getLessonsAndEvents(Authentication auth) {
        if (!(auth.getPrincipal() instanceof CustomUserDetails)) {
            throw new IllegalArgumentException(AUTH_PRINCIPAL_MSG + CustomUserDetails.class);
        }

        int accountId = ((CustomUserDetails) auth.getPrincipal()).getUserId();
        Account account = accountDAO.read(accountId);

        if (account.getGroup() == null) {
            return new ResponseEntity(new ErrorDTO(NOT_FOUND_MSG), HttpStatus.BAD_REQUEST);
        }

        int groupId = account.getGroup().getId();

        List<LessonDTO> lessons = lessonController.readLessonsByGroup(groupId).getBody();
        List<EventDTO> events = eventController.readEventsByGroup(groupId).getBody();

        return new ResponseEntity<>(new LessonsAndEventsDTO(lessons, events), HttpStatus.OK);
    }

    private void createPermission(int accountId, int groupId, boolean admin, boolean editLessons, boolean editEvents) {
        Permission newPermission = new Permission();
        newPermission.setAccountId(accountId);
        newPermission.setGroupId(groupId);
        newPermission.setAdmin(admin);
        newPermission.setLessonsEdit(editLessons);
        newPermission.setEventsEdit(editEvents);
        permissionDAO.create(newPermission);
    }

    private boolean checkGroup(GroupDTO groupDTO, ErrorDTO errorDTO) {
        if(!CommonValidator.isNameValid(groupDTO.getName())) {
            errorDTO.addMessage(INVALID_GROUP_NAME_MSG);
        }

        if(groupDTO.getId() != null) {
            Account leader = accountDAO.read(groupDTO.getLeaderId());
            if(!leader.getGroup().getId().equals(groupDTO.getId())) {
                errorDTO.addMessage(LEADER_NOT_IN_GROUP_MSG);
            }
        }

        return errorDTO.getMessages().isEmpty();
    }
}
