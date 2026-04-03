package com.example.myapplication.ui.news;

public class Headline {

    private final String title;
    private final String description;
    private final String date;
    private final int imageRes;

    public Headline(String title,
                    String description,
                    String date,
                    int imageRes) {

        this.title = title;
        this.description = description;
        this.date = date;
        this.imageRes = imageRes;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public int getImageRes() {
        return imageRes;
    }
}