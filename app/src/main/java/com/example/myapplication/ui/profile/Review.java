package com.example.myapplication.ui.profile;

import com.google.gson.annotations.SerializedName;

public class Review {

    private String id;

    @SerializedName("author_name")
    private String author;

    private int rating;
    private String text;

    @SerializedName("created_at")
    private String date;

    @SerializedName("author_id")
    private String authorId;

    public Review() {}

    public String getId()       { return id; }
    public String getAuthor()   { return author != null ? author : "Аноним"; }
    public int getRating()      { return rating; }
    public String getText()     { return text; }
    public String getDate()     { return date; }
    public String getAuthorId() { return authorId; }
}
