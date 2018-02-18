package com.company.controller.rest;

import com.company.dao.api.AccountDAO;
import com.company.dao.api.GroupDAO;
import com.company.dao.api.PermissionDAO;
import com.company.dto.GroupDTO;
import com.company.dto.converter.IEntityConverter;
import com.company.model.Account;
import com.company.model.Group;
import com.company.model.Permission;
import com.company.service.auth.CustomUserDetails;
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

    @Autowired
    public GroupRestController(AccountDAO accountDAO, GroupDAO groupDAO, PermissionDAO permissionDAO, IEntityConverter<Group, GroupDTO> groupConverter) {
        this.accountDAO = accountDAO;
        this.groupDAO = groupDAO;
        this.permissionDAO = permissionDAO;
        this.groupConverter = groupConverter;
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
        groupDAO.create(groupConverter.restore(groupDTO));
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity updateGroup(@RequestBody GroupDTO groupDTO) {
        Group group = groupConverter.restore(groupDTO);
        groupDAO.update(group);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/{groupId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteGroup(@PathVariable int groupId) {
        groupDAO.delete(groupId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/permission", method = RequestMethod.PUT)
    public ResponseEntity updatePermissions(@RequestBody List<Permission> permissions) {
        permissionDAO.update(permissions);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/member/invite", method = RequestMethod.POST)
    public ResponseEntity addMember(@RequestParam(name = "login") String login, Authentication auth) {
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
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/member", method = RequestMethod.DELETE)
    public ResponseEntity deleteMembers(@RequestBody List<Integer> memberIds) {
        for (Integer memberId : memberIds) {
            Account account = accountDAO.read(memberId);
            account.setGroup(null);
            accountDAO.update(account);
            permissionDAO.delete(permissionDAO.readByAccount(memberId));
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
