package com.example.myapplication.ui.projects;

public class Project {

    private final String title;
    private final String description;
    private final String details;
    private final String instructor;

    private final String topic;
    private final String difficulty;
    private final String deadline;
    private boolean expanded;

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
    public Project(String title,
                   String description,
                   String details,
                   String instructor,
                   String topic,
                   String difficulty,
                   String deadline) {

        this.title = title;
        this.description = description;
        this.details = details;
        this.instructor = instructor;
        this.topic = topic;
        this.difficulty = difficulty;
        this.deadline = deadline;
    }

    public String getTopic() { return topic; }
    public String getDifficulty() { return difficulty; }
    public String getDeadline() { return deadline; }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getDetails() { return details; }
    public String getInstructor() { return instructor; }
}