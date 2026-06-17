package com.example.myapplication.ui.profile;

import com.google.gson.annotations.SerializedName;

public class UserListItem {

    private String id;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("full_name")
    private String fullName;

    private String role;
    private Integer course;

    @SerializedName("group_number")
    private String groupNumber;

    @SerializedName("avatar_url")
    private String avatarUrl;

    private Double rating;

    public UserListItem() {}

    public String getId() { return id != null ? id : ""; }

    public String getFullName() {
        if (fullName != null && !fullName.trim().isEmpty()) return fullName.trim();
        String fn = firstName != null ? firstName : "";
        String ln = lastName != null ? lastName : "";
        String combined = (fn + " " + ln).trim();
        return combined.isEmpty() ? "Без имени" : combined;
    }

    public String getRole() { return role != null ? role : ""; }

    public String getSubtitle() {
        // Для студента показываем курс/группу, для преподавателя — роль
        if ("student".equalsIgnoreCase(role)) {
            StringBuilder sb = new StringBuilder("Студент");
            if (course != null) sb.append(" • ").append(course).append(" курс");
            if (groupNumber != null && !groupNumber.trim().isEmpty())
                sb.append(" • гр. ").append(groupNumber.trim());
            return sb.toString();
        } else if ("teacher".equalsIgnoreCase(role)) {
            return "Преподаватель";
        } else if ("admin".equalsIgnoreCase(role)) {
            return "Администратор";
        }
        return "";
    }

    public String getAvatarUrl() { return avatarUrl != null ? avatarUrl : ""; }

    public double getRating() { return rating != null ? rating : 0.0; }
}
