package com.example.myapplication.ui.data;

import com.example.myapplication.ui.network.RetrofitClient;
import com.example.myapplication.ui.profile.Review;
import com.example.myapplication.ui.profile.ReviewRequest;
import com.example.myapplication.ui.profile.UserListItem;

import java.util.List;

import retrofit2.Callback;

public class ReviewsRepository {

    public void getReviews(String userId, Callback<List<Review>> callback) {
        RetrofitClient.getApi().getReviews(userId).enqueue(callback);
    }

    public void createReview(String userId, int rating, String text, Callback<Review> callback) {
        RetrofitClient.getApi().createReview(userId, new ReviewRequest(rating, text)).enqueue(callback);
    }

    public void getUsers(String role, Callback<List<UserListItem>> callback) {
        RetrofitClient.getApi().getUsers(role).enqueue(callback);
    }
}
