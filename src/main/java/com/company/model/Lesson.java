package com.company.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

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

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "lesson_tags", joinColumns = @JoinColumn(name = "lesson_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags;


    public Lesson() {
    }

    public Lesson(String name, Integer room, Timestamp startDatetime, Timestamp endDatetime, String teacher, Group group, List<Tag> tags) {
        this.name = name;
        this.room = room;
        this.startDatetime = startDatetime;
        this.endDatetime = endDatetime;
        this.teacher = teacher;
        this.group = group;
        this.tags = tags;
    }

    public Lesson(Integer id, String name, Integer room, Timestamp startDatetime, Timestamp endDatetime, String teacher, Group group, List<Tag> tags) {
        this(name, room, startDatetime, endDatetime, teacher, group, tags);
        this.id = id;
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

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
