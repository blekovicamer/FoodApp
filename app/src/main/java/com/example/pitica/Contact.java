package com.example.pitica;

public class Contact {
    private String name;
    private String lastMessage;

    // Constructor: This initializes the contact with a name and message
    public Contact(String name, String lastMessage) {
        this.name = name;
        this.lastMessage = lastMessage;
    }

    // Getters: These are methods that allow the Adapter to read the data
    public String getName() {
        return name;
    }

    public String getLastMessage() {
        return lastMessage;
    }
}