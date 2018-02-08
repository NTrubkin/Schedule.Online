package com.company.controller.rest;

import com.company.dao.api.GroupDAO;
import com.company.dto.GroupDTO;
import com.company.dto.converter.IEntityConverter;
import com.company.model.Group;
import com.company.service.util.AccountIdentifyConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/group")
public class GroupRestController {

    private GroupDAO groupDAO;
    private IEntityConverter<Group, GroupDTO> groupConverter;
    private AccountIdentifyConverter accIdConverter;

    @Autowired
    public GroupRestController(GroupDAO groupDAO, IEntityConverter<Group, GroupDTO> groupConverter, AccountIdentifyConverter accIdConverter) {
        this.groupDAO = groupDAO;
        this.groupConverter = groupConverter;
        this.accIdConverter = accIdConverter;
    }

    // todo сделать запрос для начальной страницы

    @RequestMapping(value = "/{groupId}", method = RequestMethod.GET)
    public ResponseEntity<GroupDTO> readGroup(Authentication auth, @PathVariable(name = "groupId") int groupId) {
        Group group = groupDAO.read(groupId);
        return new ResponseEntity<>(groupConverter.convert(group), HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity createGroup(@RequestBody GroupDTO groupDTO, Authentication auth) {
        int id = accIdConverter.NameToId(auth.getName());
        groupDTO.setLeader_id(id);
        groupDAO.create(groupConverter.restore(groupDTO));
        return new ResponseEntity(HttpStatus.OK);
    }

    /*@RequestMapping(value = "/", method = RequestMethod.PUT)
    public ResponseEntity updateGroup() {
        throw new UnsupportedOperationException("not implemented yet");
    }*/

    @RequestMapping(value = "/{groupId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteGroup(@PathVariable(name = "groupId") int groupId) {
        groupDAO.delete(groupId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
