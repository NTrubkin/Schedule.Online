package com.company.dto;

public class GroupDTO {
    private Integer id;
    private String name;
    private Integer leaderId;

    public GroupDTO() {
    }

    public GroupDTO(Integer id, String name, Integer leaderId) {
        this.id = id;
        this.name = name;
        this.leaderId = leaderId;
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

    public int getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(Integer leaderId) {
        this.leaderId = leaderId;
    }
}
