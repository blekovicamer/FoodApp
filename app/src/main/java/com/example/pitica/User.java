package com.example.pitica;

public class User {
    private String nickname;
    private String uid;

    public User() {} // Required for Firestore

    public User(String nickname, String uid) {
        this.nickname = nickname;
        this.uid = uid;
    }

    public String getNickname() { return nickname; }
    public String getUid() { return uid; }
}