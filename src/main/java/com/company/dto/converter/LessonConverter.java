package com.company.dto.converter;

import com.company.dto.LessonDTO;
import com.company.model.Group;
import com.company.model.Lesson;
import org.hibernate.collection.internal.PersistentBag;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class LessonConverter extends EntityConverter<Lesson, LessonDTO> {
    @Override
    public LessonDTO convert(Lesson entity) {
        if (entity == null) {
            return null;
        }
        else return new LessonDTO(
                entity.getId(),
                entity.getName(),
                entity.getRoom(),
                entity.getStartDatetime(),
                entity.getEndDatetime(),
                entity.getTeacher(),
                entity.getGroup() == null ? null : entity.getGroup().getId(),
                entity.getTags()
        );
    }

    @Override
    public Lesson restore(LessonDTO dto) {
        if (dto == null) {
            return null;
        }
        else {
            Group group = new Group();
            group.setId(dto.getGroupId());
            return new Lesson(
                    dto.getName(),
                    dto.getRoom(),
                    dto.getStartDatetime(),
                    dto.getEndDatetime(),
                    dto.getTeacher(),
                    group,
                    dto.getTags()
            );
        }
    }
}
