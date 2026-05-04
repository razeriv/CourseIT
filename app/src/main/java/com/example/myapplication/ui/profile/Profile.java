package com.example.myapplication.ui.profile;

import com.google.gson.annotations.SerializedName;

public class Profile {

    @SerializedName("first_name")
    private String name;

    @SerializedName("last_name")
    private String surname;

    private String email;

    @SerializedName("course")
    private String faculty;

    private String group_number;
    private String about;
    private String role;
    private boolean is_active;

    public Profile() {}

    // Геттеры
    public String getName() {
        return name != null ? name : "";
    }

    public String getSurname() {
        return surname != null ? surname : "";
    }

    public String getEmail() {
        return email != null ? email : "";
    }

    public String getFaculty() {
        return faculty != null ? faculty : "";
    }

    public String getGroup_number() {
        return group_number != null ? group_number : "";
    }

    public String getAbout() {
        return about != null ? about : "";
    }

    public String getRole() {
        return role != null ? role : "";
    }

    public boolean isActive() {
        return is_active;
    }

    public boolean isTeacher() {
        return "teacher".equalsIgnoreCase(role);
    }

    public boolean isStudent() {
        return "student".equalsIgnoreCase(role) || role == null;
    }
}