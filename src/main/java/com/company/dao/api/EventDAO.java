package com.company.dao.api;

import com.company.model.Event;

import java.util.List;

public interface EventDAO extends IDAO<Event> {
    List<Event> readAllByGroup(int groupId);
}
