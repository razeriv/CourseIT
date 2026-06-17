package com.example.myapplication.ui.projects;

import android.os.Build;

import java.time.LocalDateTime;

public class CreateProjectRequest {

    private final String title;
    private final String description;
    private final String topic;
    private final String status;
    private final String difficulty;
    private final String deadline;
    private final String tutor;
    private final int max_students;
    private LocalDateTime created_at;
    private String requirements;

    public CreateProjectRequest(String title, String description, String topic, String status) {
        this.title = title;
        this.description = description;
        this.topic = topic;
        this.status = status;
        this.tutor = null;
        this.difficulty = null;
        this.deadline = null;
        this.max_students = 1;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.created_at = LocalDateTime.now();
        }
        this.requirements = null;
    }

    // Геттеры
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getTopic() { return topic; }
    public String getStatus() { return status; }
    public String getTutor() { return tutor; }
    public String getDifficulty() { return difficulty; }
    public String getDeadline() { return deadline; }
    public int getMax_students() { return max_students; }
    public String getRequirements() { return requirements; }
}