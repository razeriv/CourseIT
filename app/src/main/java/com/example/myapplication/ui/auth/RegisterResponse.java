package com.example.myapplication.ui.auth;

public class RegisterResponse {

    private boolean success;
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "RegisterResponse{success=" + success + ", message='" + message + "'}";
    }
}