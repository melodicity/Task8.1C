package com.example.task81c;

public class ChatMessage {
    private final String content;
    private final String sender;

    public ChatMessage(String content, String sender) {
        this.content = content;
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public String getSender() {
        return sender;
    }
}