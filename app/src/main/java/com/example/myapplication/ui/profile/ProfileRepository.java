package com.example.myapplication.ui.profile;

import com.example.myapplication.ui.network.RetrofitClient;
import com.example.myapplication.ui.projects.UpdateProfileRequest;
import com.example.myapplication.ui.text.UpdateAboutRequest;

import retrofit2.Callback;

public class ProfileRepository {

    public void getProfile(Callback<Profile> callback) {
        RetrofitClient.getApi().getProfile().enqueue(callback);
    }

    public void updateAbout(String aboutText, Callback<Profile> callback) {
        UpdateAboutRequest request = new UpdateAboutRequest(aboutText);
        RetrofitClient.getApi().updateAbout(request).enqueue(callback);
    }

    public void updateProfile(String email, String first_name, String last_name, String group_number, String course, String avatar_url, String password_hash, Callback<Profile> callback){
        UpdateProfileRequest request = new UpdateProfileRequest(email, first_name, last_name, group_number, course, avatar_url, password_hash);
        RetrofitClient.getApi().updateProfile(request).enqueue(callback);
    }
}