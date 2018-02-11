package com.company.dto;

import com.company.model.Tag;

import java.sql.Timestamp;
import java.util.List;

public class LessonDTO {
    private Integer id;
    private String name;
    private Integer room;
    private Timestamp startDatetime;
    private Timestamp endDatetime;
    private String teacher;
    private Integer groupId;
    private List<Tag> tags;

    public LessonDTO() {
    }

    public LessonDTO(Integer id, String name, Integer room, Timestamp startDatetime, Timestamp endDatetime, String teacher, Integer groupId, List<Tag> tags) {
        this.id = id;
        this.name = name;
        this.room = room;
        this.startDatetime = startDatetime;
        this.endDatetime = endDatetime;
        this.teacher = teacher;
        this.groupId = groupId;
        this.tags = tags;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRoom() {
        return room;
    }

    public void setRoom(Integer room) {
        this.room = room;
    }

    public Timestamp getStartDatetime() {
        return startDatetime;
    }

    public void setStartDatetime(Timestamp startDatetime) {
        this.startDatetime = startDatetime;
    }

    public Timestamp getEndDatetime() {
        return endDatetime;
    }

    public void setEndDatetime(Timestamp endDatetime) {
        this.endDatetime = endDatetime;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
