package com.example.myapplication.ui.profile;

import com.example.myapplication.ui.network.RetrofitClient;
import com.example.myapplication.ui.text.UpdateAboutRequest;

import retrofit2.Callback;

public class ProfileRepository {

    public void getProfile(Callback<Profile> callback) {
        RetrofitClient.getApi().getProfile().enqueue(callback);
    }

    public void updateAbout(String aboutText, Callback<Profile> callback) {
        UpdateAboutRequest request = new UpdateAboutRequest(aboutText);
        RetrofitClient.getApi().updateProfile(request).enqueue(callback);
    }
}