package com.company.dto;

import com.company.model.Event;
import com.company.model.Group;
import com.company.model.Lesson;

import java.util.List;

/**
 * DTO контейнер для всех данных, которые необходимы для отрисовки начальной страницы
 */
public class MainPageDTO {
    private PrivateAccountDTO user;
    private GroupDTO group;
    private List<LessonDTO> lessons;
    private List<EventDTO> events;

    public MainPageDTO() {
    }

    public MainPageDTO(PrivateAccountDTO user, GroupDTO group, List<LessonDTO> lessons, List<EventDTO> events) {
        this.user = user;
        this.group = group;
        this.lessons = lessons;
        this.events = events;
    }

    public PrivateAccountDTO getUser() {
        return user;
    }

    public void setUser(PrivateAccountDTO user) {
        this.user = user;
    }

    public GroupDTO getGroup() {
        return group;
    }

    public void setGroup(GroupDTO group) {
        this.group = group;
    }

    public List<LessonDTO> getLessons() {
        return lessons;
    }

    public void setLessons(List<LessonDTO> lessons) {
        this.lessons = lessons;
    }

    public List<EventDTO> getEvents() {
        return events;
    }

    public void setEvents(List<EventDTO> events) {
        this.events = events;
    }
}
