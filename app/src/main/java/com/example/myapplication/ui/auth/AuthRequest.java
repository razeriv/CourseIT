package com.example.myapplication.ui.auth;

public class AuthRequest {

    private final String email;
    private final String password;

    public AuthRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "AuthRequest{email='" + email + "'}";
    }
}