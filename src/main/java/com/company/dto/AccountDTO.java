package com.company.dto;

/**
 * DTO контейнер для аккаунтов других пользователей
 * Исключена вся приватная информация об аккаунте
 */
public class AccountDTO {
    private int id;
    private String firstName;
    private String secondName;

    public AccountDTO() {
    }

    public AccountDTO(int id, String firstName, String secondName) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
