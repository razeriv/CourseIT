package com.example.myapplication.ui.profile;

import com.example.myapplication.ui.network.RetrofitClient;
import com.example.myapplication.ui.projects.UpdateProfileRequest;

import retrofit2.Callback;

public class ProfileRepository {

    public void getProfile(Callback<Profile> callback) {
        RetrofitClient.getApi().getProfile().enqueue(callback);
    }

    public void updateProfile(UpdateProfileRequest request, Callback<Profile> callback) {
        RetrofitClient.getApi().updateProfile(request).enqueue(callback);
    }
}