package com.company.model;

import org.springframework.security.access.method.P;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "groups", schema = "public")
public class Group {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @ManyToOne(targetEntity = Account.class)
    @JoinColumn(name = "leader_id")
    private Account leader;

    public Group() {
    }

    public Group(String name, Account leader) {
        this.name = name;
        this.leader = leader;
    }

    public Group(Integer id, String name, Account leader) {
        this(name, leader);
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

    public Account getLeader() {
        return leader;
    }

    public void setLeader(Account leader) {
        this.leader = leader;
    }
}
