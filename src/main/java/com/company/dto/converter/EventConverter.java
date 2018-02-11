package com.company.dto.converter;

import com.company.dto.EventDTO;
import com.company.model.Event;
import com.company.model.Group;
import org.hibernate.collection.internal.PersistentBag;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class EventConverter extends EntityConverter<Event, EventDTO> {

    @Override
    public EventDTO convert(Event entity) {
        if (entity == null) {
            return null;
        }
        else return new EventDTO(
                entity.getId(),
                entity.getName(),
                entity.getStartDatetime(),
                entity.getPlace(),
                entity.getGroup() == null ? null : entity.getGroup().getId(),
                entity.getDescription(),
                entity.getTags()
        );
    }

    @Override
    public Event restore(EventDTO dto) {
        if (dto == null) {
            return null;
        }
        else {
            Group group = new Group();
            group.setId(dto.getGroupId());
            return new Event(
                    dto.getName(),
                    dto.getStartDatetime(),
                    dto.getPlace(),
                    group,
                    dto.getDescription(),
                    dto.getTags()
            );
        }
    }
}
