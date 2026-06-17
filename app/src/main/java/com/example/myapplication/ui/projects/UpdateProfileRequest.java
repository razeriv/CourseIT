package com.example.myapplication.ui.projects;  // или лучше переместить в ui/profile

import com.google.gson.annotations.SerializedName;

public class UpdateProfileRequest {

    @SerializedName("about")
    private String about;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("course")
    private String course;

    @SerializedName("group_number")
    private String groupNumber;

    @SerializedName("avatar_url")
    private String avatarUrl;

    // Конструкторы
    public UpdateProfileRequest() {}

    // Для обновления "О себе"
    public UpdateProfileRequest(String about) {
        this.about = about;
    }

    // Для полного обновления профиля
    public UpdateProfileRequest(String firstName, String lastName, String course,
                                String groupNumber, String avatarUrl, String about) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.course = course;
        this.groupNumber = groupNumber;
        this.avatarUrl = avatarUrl;
        this.about = about;
    }

    // Геттеры
    public String getAbout() { return about; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getCourse() { return course; }
    public String getGroupNumber() { return groupNumber; }
    public String getAvatarUrl() { return avatarUrl; }
}