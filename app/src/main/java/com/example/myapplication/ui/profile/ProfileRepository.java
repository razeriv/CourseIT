package com.example.myapplication.ui.profile;

import com.example.myapplication.ui.network.RetrofitClient;

import retrofit2.Callback;

public class ProfileRepository {

    public void getProfile(Callback<Profile> callback) {
        RetrofitClient.getApi().getProfile().enqueue(callback);
    }
}