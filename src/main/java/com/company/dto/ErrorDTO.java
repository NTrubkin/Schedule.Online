package com.company.dto;

import java.util.ArrayList;
import java.util.List;

public class ErrorDTO {
    private List<String> messages;

    public ErrorDTO() {
        messages = new ArrayList<>();
    }

    public ErrorDTO(String errorMessage) {
        this();
        messages.add(errorMessage);
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
