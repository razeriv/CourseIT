// Файл: NewsItem.java
package com.example.myapplication.ui.news;

public class NewsItem {
    private String title;
    private String description;
    private String date;
    private int imageResource;

    public NewsItem(String title, String description, String date, int imageResource) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.imageResource = imageResource;
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

    public int getImageResource() {
        return imageResource;
    }
}