package com.example.myapplication.ui.profile;

public class Portfolio {
    private final String title;
    private final String description;
    private final String topic;
    private final String status;
    private final String deadline;
    private final String difficulty;

    public Portfolio(String title, String description, String topic,
                   String status, String deadline, String difficulty) {
        this.title = title;
        this.description = description;
        this.topic = topic;
        this.status = status;
        this.deadline = deadline;
        this.difficulty = difficulty;
    }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getTopic() { return topic; }
    public String getStatus() { return status; }
    public String getDeadline() { return deadline; }
    public String getDifficulty() { return difficulty; }
}
