package com.company.dto;

import java.util.List;

public class LessonsAndEventsDTO {
    private List<LessonDTO> lessons;
    private List<EventDTO> events;

    public LessonsAndEventsDTO() {
    }

    public LessonsAndEventsDTO(List<LessonDTO> lessons, List<EventDTO> events) {
        this.lessons = lessons;
        this.events = events;
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
