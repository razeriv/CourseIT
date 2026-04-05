package com.example.myapplication.ui.data;

import androidx.lifecycle.ViewModel;

import com.example.myapplication.ui.profile.Review;

import java.util.List;

public class ReviewsViewModel extends ViewModel {

    private ReviewsRepository repository;

    public ReviewsViewModel() {
        repository = new ReviewsRepository();
    }

    public List<Review> getReviews() {
        return repository.getReviews();
    }
}
