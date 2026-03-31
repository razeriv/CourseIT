package com.example.myapplication.ui.chats;

public class ChatItem {

    public String name;
    public String project;
    public String lastMessage;
    public int unreadCount;

    public ChatItem(String name, String project, String lastMessage, int unreadCount) {
        this.name = name;
        this.project = project;
        this.lastMessage = lastMessage;
        this.unreadCount = unreadCount;
    }
}