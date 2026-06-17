package com.example.myapplication.ui.network;

import com.example.myapplication.ui.auth.AuthRequest;
import com.example.myapplication.ui.auth.AuthResponse;
import com.example.myapplication.ui.auth.RegisterRequest;
import com.example.myapplication.ui.news.Headline;
import com.example.myapplication.ui.profile.Profile;
import com.example.myapplication.ui.profile.Review;
import com.example.myapplication.ui.profile.ReviewRequest;
import com.example.myapplication.ui.profile.UserListItem;
import com.example.myapplication.ui.projects.CreateProjectRequest;
import com.example.myapplication.ui.projects.Project;
import com.example.myapplication.ui.projects.UpdateProfileRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @POST("/api/v1/register")
    Call<AuthResponse> register(@Body RegisterRequest request);

    @POST("/api/v1/login")
    Call<AuthResponse> login(@Body AuthRequest request);

    // --- Новости ---
    @GET("/api/v1/news")
    Call<List<Headline>> getNews();

    // --- Проекты ---
    @GET("/api/v1/projects")
    Call<List<Project>> getProjects();

    @POST("/api/v1/projects")
    Call<Project> createProject(@Body CreateProjectRequest request);

    // --- Профиль ---
    @GET("/api/v1/profile")
    Call<Profile> getProfile();

    @PATCH("/api/v1/profile")
    Call<Profile> updateProfile(@Body UpdateProfileRequest request);

    // --- Отзывы ---
    @GET("/api/v1/reviews/{userId}")
    Call<List<Review>> getReviews(@Path("userId") String userId);

    @POST("/api/v1/reviews/{userId}")
    Call<Review> createReview(@Path("userId") String userId, @Body ReviewRequest request);

    // --- Список пользователей (для выбора получателя отзыва) ---
    // role не обязателен: если не передан, сервер вернёт противоположную роль.
    @GET("/api/v1/users")
    Call<List<UserListItem>> getUsers(@Query("role") String role);
}
