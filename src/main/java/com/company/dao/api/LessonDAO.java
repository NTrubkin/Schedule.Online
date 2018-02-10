package com.company.dao.api;

import com.company.model.Lesson;

import java.util.List;

public interface LessonDAO extends IDAO<Lesson> {
    List<Lesson> readAllByGroup(int groupId);
}
