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
    private Group group;
    private List<Lesson> lessons;
    private List<Event> events;

    public MainPageDTO() {
    }

    public MainPageDTO(PrivateAccountDTO user, Group group, List<Lesson> lessons, List<Event> events) {
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

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
