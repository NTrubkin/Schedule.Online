package com.company.dto;

import java.util.ArrayList;
import java.util.List;

public class ErrorMessageDTO {
    private List<String> messages;

    public ErrorMessageDTO() {
        // pojo
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public void addMessage(String message) {
        if( messages == null) {
            messages = new ArrayList<>();
        }
        messages.add(message);
    }
}
