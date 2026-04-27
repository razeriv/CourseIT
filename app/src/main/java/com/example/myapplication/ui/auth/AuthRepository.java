package com.example.myapplication.ui.auth;

import com.example.myapplication.ui.network.RetrofitClient;

import retrofit2.Callback;

public class AuthRepository {

    public void login(String email, String password, Callback<AuthResponse> callback) {
        AuthRequest request = new AuthRequest(email, password);
        RetrofitClient.getApi().login(request).enqueue(callback);
    }

    public void register(String firstName,
                         String lastName,
                         String email,
                         String course,
                         String groupNumber,
                         String password,
                         Callback<AuthResponse> callback) {

        RegisterRequest request = new RegisterRequest(
                firstName,
                lastName,
                email,
                course,
                groupNumber,
                password
        );

        RetrofitClient.getApi().register(request).enqueue(callback);
    }
}