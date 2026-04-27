package com.example.myapplication.ui.auth;

import com.google.gson.annotations.SerializedName;

public class RegisterRequest {

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("email")
    private String email;

    @SerializedName("course")
    private String course;

    @SerializedName("group_number")
    private String groupNumber;

    @SerializedName("password")
    private String password;

    // Конструктор
    public RegisterRequest(String firstName, String lastName, String email,
                           String course, String groupNumber, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.course = course;
        this.groupNumber = groupNumber;
        this.password = password;
    }
}