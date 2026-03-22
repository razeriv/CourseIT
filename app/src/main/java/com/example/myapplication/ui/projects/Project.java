package com.example.myapplication.ui.projects;

public class Project {
    private final String title;
    private final String description;
    private final String details;
    private final String instructor;

    public Project(String title, String description, String details, String instructor) {
        this.title = title;
        this.description = description;
        this.details = details;
        this.instructor = instructor;
    }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getDetails() { return details; }

    public String getInstructor() {
        return instructor;
    }
}