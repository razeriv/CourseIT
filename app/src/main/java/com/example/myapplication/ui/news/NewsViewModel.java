package com.example.myapplication.ui.news;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class NewsViewModel extends ViewModel {

    private MutableLiveData<List<NewsItem>> newsList;

    public LiveData<List<NewsItem>> getNewsList() {
        if (newsList == null) {
            newsList = new MutableLiveData<>();
            loadNews();
        }
        return newsList;
    }

    private void loadNews() {
        List<NewsItem> data = new ArrayList<>();
        data.add(new NewsItem("Новость 1", "Описание...", "Сегодня", R.drawable.avatar_1));
        data.add(new NewsItem("Новость 2", "Описание...", "Сегодня", R.drawable.avatar_2));
        data.add(new NewsItem("Новость 3", "Описание...", "Сегодня", R.drawable.avatar_3));
        data.add(new NewsItem("Новость 4", "Описание...", "Сегодня", R.drawable.avatar_4));

        newsList.setValue(data);
    }
}