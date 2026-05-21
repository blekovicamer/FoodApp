package com.example.pitica;

import java.util.List;

public class Chat {
    private String lastMessage;
    private String chatId;
    private String receiverName;
    private long lastTimestamp;
    private List<String> participants;

    public Chat() {} // Required for Firestore

    public Chat(String lastMessage, String chatId, String receiverName, long lastTimestamp, List<String> participants) {
        this.lastMessage = lastMessage;
        this.chatId = chatId;
        this.receiverName = receiverName;
        this.lastTimestamp = lastTimestamp;
        this.participants = participants;
    }

    public String getLastMessage() { return lastMessage; }
    public String getChatId() { return chatId; }
    public String getReceiverName() { return receiverName; }
}