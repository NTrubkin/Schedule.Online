package com.company.dto.converter;

import com.company.dto.GroupDTO;
import com.company.model.Account;
import com.company.model.Group;
import org.springframework.stereotype.Service;

@Service
public class GroupConverter extends EntityConverter<Group, GroupDTO> {
    @Override
    public GroupDTO convert(Group entity) {
        if (entity == null) return null;
        else return new GroupDTO(
                entity.getId(),
                entity.getName(),
                entity.getLeader() == null ? null : entity.getLeader().getId()
        );
    }

    @Override
    public Group restore(GroupDTO dto) {
        if (dto == null) {
            return null;
        }
        else {
            Account leader = new Account();
            leader.setId(dto.getLeaderId());
            return new Group(
                    dto.getId(),
                    dto.getName(),
                    leader
            );
        }
    }
}
