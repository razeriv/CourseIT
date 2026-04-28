package com.example.myapplication.ui.projects;

public class CreateProjectRequest {

    private final String title;
    private final String description;
    private final String topic;
    private final String status;
    private final String difficulty;
    private final String deadline;

    public CreateProjectRequest(String title, String description, String topic, String status) {
        this.title = title;
        this.description = description;
        this.topic = topic;
        this.status = status;
        this.difficulty = null;
        this.deadline = null;
    }

    // Геттеры
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getTopic() { return topic; }
    public String getStatus() { return status; }
    public String getDifficulty() { return difficulty; }
    public String getDeadline() { return deadline; }
}