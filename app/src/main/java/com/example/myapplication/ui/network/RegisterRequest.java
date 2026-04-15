package com.example.myapplication.ui.network;

public class RegisterRequest {

    private final String name;
    private final String surname;
    private final String email;
    private final String faculty;
    private final String group;
    private final String password;

    public RegisterRequest(String name,
                           String surname,
                           String email,
                           String faculty,
                           String group,
                           String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.faculty = faculty;
        this.group = group;
        this.password = password;
    }

    public String getName() { return name; }
    public String getSurname() { return surname; }
    public String getEmail() { return email; }
    public String getFaculty() { return faculty; }
    public String getGroup() { return group; }
    public String getPassword() { return password; }
}