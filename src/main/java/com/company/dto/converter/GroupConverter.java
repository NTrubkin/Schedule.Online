package com.company.dto.converter;

import com.company.dto.GroupDTO;
import com.company.model.Account;
import com.company.model.Group;
import org.springframework.stereotype.Service;

@Service
public class GroupConverter extends EntityConverter<Group, GroupDTO> {
    @Override
    public GroupDTO convert(Group entity) {
        return entity == null ? null : new GroupDTO(
                entity.getId(),
                entity.getName(),
                entity.getLeader().getId()
        );
    }

    @Override
    public Group restore(GroupDTO dto) {
        if (dto == null) {
            return null;
        }

        Account leader = new Account();
        leader.setId(dto.getLeader_id());
        return new Group(
                dto.getName(),
                leader
        );
    }
}
