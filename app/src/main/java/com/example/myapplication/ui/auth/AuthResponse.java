package com.example.myapplication.ui.auth;

import com.google.gson.annotations.SerializedName;

public class AuthResponse {

    @SerializedName("token")
    private String token;

    @SerializedName("message")
    private String message;

    public String getToken() {
        return token;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "AuthResponse{token='" + token + "', message='" + message + "'}";
    }
}