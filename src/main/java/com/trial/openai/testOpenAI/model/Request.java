package com.trial.openai.testOpenAI.model;

import java.util.*;

public class Request {

    private String model;
    private List<Message> messages;
    
    public String getModel() {
        return model;
    }
    public List<Message> getMessages() {
        return messages;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
    
}
