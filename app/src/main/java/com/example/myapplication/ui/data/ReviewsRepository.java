package com.example.myapplication.ui.data;

import com.example.myapplication.ui.profile.Review;

import java.util.ArrayList;
import java.util.List;

public class ReviewsRepository {

    public List<Review> getReviews() {

        List<Review> reviews = new ArrayList<>();

        reviews.add(new Review(
                "Мусаев А. А.",
                5,
                "Работа с Иваном прошла продуктивно! Всегда был на связи, смогли обсудить ключевые аспекты задачи.",
                "26.03.2026"
        ));

        reviews.add(new Review(
                "Мусаев А. А.",
                2,
                "Работа прошла нормально.",
                "26.03.2026"
        ));

        reviews.add(new Review(
                "Мусаев А. А.",
                4,
                "Работа с Иваном прошла продуктивно.",
                "26.03.2026"
        ));

        return reviews;
    }
}