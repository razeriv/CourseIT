package com.example.myapplication.ui.auth;

public class AuthResponse {

    private String token;
    private String message;

    public String getToken() {
        return token;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "AuthResponse{token='" + token + "'}";
    }
}