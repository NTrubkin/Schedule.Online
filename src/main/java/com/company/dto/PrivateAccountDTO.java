package com.company.dto;

/**
 * DTO контейнер для аккаунта пользователя
 * Нужен чтобы исключить из сущности Account пароль (пассхэш)
 */
public class PrivateAccountDTO {
    private Integer id;
    private String firstName;
    private String secondName;
    private String email;
    private Long phoneNumber;
    private Integer groupId;
    private Boolean settingsNotification;
    private Boolean scheduleNotification;



    public PrivateAccountDTO() {
    }

    public PrivateAccountDTO(Integer id, String firstName, String secondName, String email, Long phoneNumber, Integer groupId, Boolean settingsNotification, Boolean scheduleNotification) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.groupId = groupId;
        this.settingsNotification = settingsNotification;
        this.scheduleNotification = scheduleNotification;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Boolean getSettingsNotification() {
        return settingsNotification;
    }

    public void setSettingsNotification(Boolean settingsNotification) {
        this.settingsNotification = settingsNotification;
    }

    public Boolean getScheduleNotification() {
        return scheduleNotification;
    }

    public void setScheduleNotification(Boolean scheduleNotification) {
        this.scheduleNotification = scheduleNotification;
    }
}
