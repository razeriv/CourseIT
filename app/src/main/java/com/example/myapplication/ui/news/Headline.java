package com.example.myapplication.ui.news;

import com.google.gson.annotations.SerializedName;

public class Headline {

    private String title;
    private String description;

    @SerializedName("published_at")
    private String date;

    @SerializedName("image_url")
    private String imageUrl;

    public Headline() {}

    public String getTitle()    { return title; }
    public String getDescription() { return description; }
    public String getDate()     { return date; }
    public String getImageUrl() { return imageUrl; }
}
