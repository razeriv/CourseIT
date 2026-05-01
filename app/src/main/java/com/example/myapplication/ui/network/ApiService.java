package com.example.myapplication.ui.network;

import com.example.myapplication.ui.auth.AuthRequest;
import com.example.myapplication.ui.auth.AuthResponse;
import com.example.myapplication.ui.auth.RegisterRequest;
import com.example.myapplication.ui.news.Headline;
import com.example.myapplication.ui.profile.Profile;
import com.example.myapplication.ui.projects.CreateProjectRequest;
import com.example.myapplication.ui.projects.Project;
import com.example.myapplication.ui.text.UpdateAboutRequest;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;

public interface ApiService {

    @POST("/register")
    Call<AuthResponse> register(@Body RegisterRequest request);

    @POST("/login")
    Call<AuthResponse> login(@Body AuthRequest request);

    @GET("/news")
    Call<List<Headline>> getNews();

    @GET("/projects")
    Call<List<Project>> getProjects();

    @GET("/profile")
    Call<Profile> getProfile();

    @POST("/projects")
    Call<Project> createProject(@Body CreateProjectRequest request);

    @PATCH("/profile")
    Call<Profile> updateProfile(@Body UpdateAboutRequest request);
}