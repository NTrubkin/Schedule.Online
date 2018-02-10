package com.company.dto;

/**
 * DTO контейнер для аккаунтов других пользователей
 * Исключена вся приватная информация об аккаунте
 */
public class AccountDTO {
    private Integer id;
    private String firstName;
    private String secondName;

    public AccountDTO() {
    }

    public AccountDTO(Integer id, String firstName, String secondName) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }
}
