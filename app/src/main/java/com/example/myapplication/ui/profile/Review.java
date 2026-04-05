package com.example.myapplication.ui.profile;

public class Review {

    private final String author;
    private final int rating;
    private final String text;
    private final String date;

    public Review(String author, int rating, String text, String date) {
        this.author = author;
        this.rating = rating;
        this.text = text;
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public int getRating() {
        return rating;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }
}