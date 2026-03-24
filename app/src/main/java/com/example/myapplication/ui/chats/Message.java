package com.example.myapplication.ui.chats;

public class Message {
    public String text;
    public boolean isMine;

    public Message(String text, boolean isMine) {
        this.text = text;
        this.isMine = isMine;
    }
}
