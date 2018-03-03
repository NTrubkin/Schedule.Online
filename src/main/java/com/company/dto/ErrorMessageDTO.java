package com.company.dto;

import java.util.ArrayList;
import java.util.List;

public class ErrorMessageDTO {
    private List<String> messages;

    public ErrorMessageDTO() {
        messages = new ArrayList<>();
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public void addMessage(String message) {
        messages.add(message);
    }
}
