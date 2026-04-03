package com.example.myapplication.ui.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.ui.news.Headline;

import java.util.List;

public class NewsViewModel extends ViewModel {

    private final MutableLiveData<List<Headline>> news = new MutableLiveData<>();

    public NewsViewModel() {
        loadNews();
    }

    public void loadNews() {
        news.setValue(NewsRepository.getNews());
    }

    public void setNews(List<Headline> list) {
        news.setValue(list);
    }

    public LiveData<List<Headline>> getNews() {
        return news;
    }
}