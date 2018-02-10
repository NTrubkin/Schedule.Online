package com.company.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "lessons", schema = "public")
public class Lesson {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "room")
    private Integer room;

    @Column(name = "start_datetime")
    private Timestamp startDatetime;

    @Column(name = "end_datetime")
    private Timestamp endDatetime;

    @Column(name = "teacher")
    private String teacher;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;


    public Lesson() {
    }

    public Lesson(String name, Integer room, Timestamp startDatetime, Timestamp endDatetime, String teacher, Group group) {
        this.name = name;
        this.room = room;
        this.startDatetime = startDatetime;
        this.endDatetime = endDatetime;
        this.teacher = teacher;
        this.group = group;
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

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
