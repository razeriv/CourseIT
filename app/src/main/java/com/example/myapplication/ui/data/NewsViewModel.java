package com.example.myapplication.ui.data;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.ui.news.Headline;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class NewsViewModel extends ViewModel {

    private final MutableLiveData<List<Headline>> news = new MutableLiveData<>();

    private final NewsRepository repository = new NewsRepository();

    public LiveData<List<Headline>> getNews() {
        return news;
    }

    public void loadNews() {
        repository.getNews(new retrofit2.Callback<List<Headline>>() {
            @Override
            public void onResponse(@NonNull Call<List<Headline>> call, @NonNull Response<List<Headline>> response) {
                if (response.isSuccessful()) {
                    news.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Headline>> call, @NonNull Throwable t) {
                news.setValue(new ArrayList<>());
            }
        });
    }
}