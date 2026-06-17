package com.example.myapplication.ui.profile;

public class ReviewRequest {
    private int rating;
    private String text;

    public ReviewRequest(int rating, String text) {
        this.rating = rating;
        this.text = text;
    }

    public int getRating() { return rating; }
    public String getText() { return text; }
}
