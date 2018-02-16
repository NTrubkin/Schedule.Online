package com.company.model;

import javax.persistence.*;

@Entity
@Table(name = "permissions", schema = "public")
public class Permission {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "account_id")
    private Integer accountId;

    @Column(name = "group_id")
    private Integer groupId;

    @Column(name = "admin")
    private Boolean admin;

    @Column(name = "lessons_edit")
    private Boolean lessonsEdit;

    @Column(name = "events_edit")
    private Boolean eventsEdit;

    public Permission() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public Boolean getLessonsEdit() {
        return lessonsEdit;
    }

    public void setLessonsEdit(Boolean lessonsEdit) {
        this.lessonsEdit = lessonsEdit;
    }

    public Boolean getEventsEdit() {
        return eventsEdit;
    }

    public void setEventsEdit(Boolean eventsEdit) {
        this.eventsEdit = eventsEdit;
    }
}
