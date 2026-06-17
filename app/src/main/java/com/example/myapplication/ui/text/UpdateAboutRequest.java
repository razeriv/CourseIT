package com.example.myapplication.ui.text;

public class UpdateAboutRequest {

    private final String about;

    public UpdateAboutRequest(String about) {
        this.about = about;
    }

    public String getAbout() {
        return about;
    }
}