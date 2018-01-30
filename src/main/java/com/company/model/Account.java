package com.company.model;

import javax.persistence.*;

@Entity
@Table(name = "accounts", schema = "public")
public class Account {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // todo переименовать name в login
    @Column(name = "name")
    private String name;

    @Column(name = "passhash")
    private String passhash;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    public Account() {
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

    public void setName(String nickname) {
        this.name = nickname;
    }

    public String getPasshash() {
        return passhash;
    }

    public void setPasshash(String passhash) {
        this.passhash = passhash;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
