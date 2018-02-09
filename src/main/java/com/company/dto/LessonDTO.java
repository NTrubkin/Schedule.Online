package com.company.dto;

import java.sql.Timestamp;

public class LessonDTO {
    private int id;
    private String name;
    private int room;
    private Timestamp datetime;
    private String teacher;
    private int groupId;

    public LessonDTO() {
    }

    public LessonDTO(int id, String name, int room, Timestamp datetime, String teacher, int groupId) {
        this.id = id;
        this.name = name;
        this.room = room;
        this.datetime = datetime;
        this.teacher = teacher;
        this.groupId = groupId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
}
