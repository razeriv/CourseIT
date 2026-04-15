package com.example.myapplication.ui.auth;

import com.example.myapplication.ui.network.RegisterRequest;
import com.example.myapplication.ui.network.RetrofitClient;

import retrofit2.Callback;

public class AuthRepository {

    public void login(String email, String password,
                      Callback<AuthResponse> callback) {

        AuthRequest request = new AuthRequest(email, password);
        RetrofitClient.getApi().login(request).enqueue(callback);
    }

    public void register(String name,
                         String surname,
                         String email,
                         String faculty,
                         String group,
                         String password,
                         Callback<Void> callback) {

        RetrofitClient.getApi().register(new RegisterRequest(
                name,
                surname,
                email,
                faculty,
                group,
                password
        )).enqueue(callback);
    }
}