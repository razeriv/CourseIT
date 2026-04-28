package com.example.myapplication.ui.profile;

public class Profile {

    private final String name;
    private final String surname;
    private final String email;
    private final String faculty;
    private final String group_number;
    private final String about;
    private final String role;
    private final boolean is_active;

    public Profile(String name, String surname, String email, String faculty,
                   String group_number, String about, String role, boolean is_active) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.faculty = faculty;
        this.group_number = group_number;
        this.about = about;
        this.role = role;
        this.is_active = is_active;
    }

    public String getName() { return name; }
    public String getSurname() { return surname; }
    public String getEmail() { return email; }
    public String getFaculty() { return faculty; }
    public String getGroup_number() { return group_number; }
    public String getAbout() { return about; }
    public String getRole() { return role; }
    public boolean isActive() { return is_active; }

    public boolean isTeacher() {
        return "teacher".equalsIgnoreCase(role);
    }

    public boolean isStudent() {
        return "student".equalsIgnoreCase(role) || role == null;
    }
}