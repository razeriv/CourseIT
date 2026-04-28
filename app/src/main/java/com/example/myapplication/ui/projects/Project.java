package com.example.myapplication.ui.projects;

import androidx.annotation.NonNull;

public class Project {

    private String title;
    private String description;
    private String requirements;
    private String details;
    private String instructor;
    private String topic;
    private String difficulty;
    private String deadline;

    public Project() {}

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getDetails() { return details; }
    public String getInstructor() { return instructor; }
    public String getRequirements() { return requirements; }
    public String getTopic() { return topic; }
    public String getDifficulty() { return difficulty; }
    public String getDeadline() { return deadline; }

    @NonNull
    @Override
    public String toString() {
        return "Project{" +
                "title='" + title + '\'' +
                ", instructor='" + instructor + '\'' +
                ", difficulty='" + difficulty + '\'' +
                ", deadline='" + deadline + '\'' +
                '}';
    }
}