package com.example.myapplication.ui.network;

import com.example.myapplication.ui.auth.AuthRequest;
import com.example.myapplication.ui.auth.AuthResponse;
import com.example.myapplication.ui.news.Headline;
import com.example.myapplication.ui.projects.Project;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @GET("projects")
    Call<List<Project>> getProjects();

    @GET("news")
    Call<List<Headline>> getNews();

    @POST("login")
    Call<AuthResponse> login(@Body AuthRequest request);

    @POST("register")
    Call<Void> register(@Body RegisterRequest request);
}
