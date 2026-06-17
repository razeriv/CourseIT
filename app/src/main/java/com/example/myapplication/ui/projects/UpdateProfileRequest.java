package com.example.myapplication.ui.projects;

public class UpdateProfileRequest {
    public String email;
    public String first_name;
    public String last_name;
    public String group_number;
    public String course;
    public String avatar_url;
    public String password_hash;
    public UpdateProfileRequest(String email, String first_name, String last_name, String group_number, String course, String avatar_url, String password_hash){
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.group_number = group_number;
        this.course = course;
        this.avatar_url = avatar_url;
        this.password_hash = password_hash;
    }

    public String getEmail() {
        return email;
    }
    public String getFirst_name(){
        return first_name;
    }
    public String getLast_name(){
        return last_name;
    }
    public String getGroup_number(){
        return group_number;
    }
    public String getCourse(){
        return course;
    }
    public String getAvatar_url(){
        return avatar_url;
    }
    public String getPassword_hash(){
        return password_hash;
    }
}
