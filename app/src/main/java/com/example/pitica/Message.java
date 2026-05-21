package com.example.pitica;

public class Message {
    private String senderId;
    private String messageText;
    private long timestamp;

    // Required empty constructor for Firebase
    public Message() {
    }

    // Constructor to easily create a message
    public Message(String senderId, String messageText, long timestamp) {
        this.senderId = senderId;
        this.messageText = messageText;
        this.timestamp = timestamp;
    }

    // Getters are required for Firebase to read the data
    public String getSenderId() {
        return senderId;
    }

    public String getMessageText() {
        return messageText;
    }

    public long getTimestamp() {
        return timestamp;
    }
}