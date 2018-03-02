package com.company.service.sender;

public interface MessageSender {
    void send(String address, String subject, String message);

    boolean isAddressValid(String address);

    boolean isMessageValid(String message);
}
