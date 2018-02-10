package com.company.dto;


import java.sql.Timestamp;

public class EventDTO {

    private Integer id;
    private String name;
    private Timestamp startDatetime;
    private String place;
    private Integer groupId;
    private String description;

    public EventDTO() {
    }

    public EventDTO(Integer id, String name, Timestamp startDatetime, String place, Integer groupId, String description) {
        this.id = id;
        this.name = name;
        this.startDatetime = startDatetime;
        this.place = place;
        this.groupId = groupId;
        this.description = description;
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

    public Timestamp getStartDatetime() {
        return startDatetime;
    }

    public void setStartDatetime(Timestamp startDatetime) {
        this.startDatetime = startDatetime;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
