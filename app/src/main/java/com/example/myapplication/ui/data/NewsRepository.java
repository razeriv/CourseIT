package com.example.myapplication.ui.data;

import com.example.myapplication.ui.network.RetrofitClient;
import com.example.myapplication.ui.news.Headline;

import java.util.List;

import retrofit2.Callback;

public class NewsRepository {
    public void getNews(Callback<List<Headline>> callback) {
        RetrofitClient.getApi().getNews().enqueue(callback);
    }
}
