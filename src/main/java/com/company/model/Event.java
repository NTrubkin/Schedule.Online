package com.company.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "events", schema = "public")
public class Event {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "start_datetime")
    private Timestamp startDatetime;

    @Column(name = "place")
    private String place;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    // todo экранирование инжекции (не только в description)
    @Column(name = "description")
    private String description;

    public Event() {
    }

    public Event(String name, Timestamp startDatetime, String place, Group group, String description) {
        this.name = name;
        this.startDatetime = startDatetime;
        this.place = place;
        this.group = group;
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

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
