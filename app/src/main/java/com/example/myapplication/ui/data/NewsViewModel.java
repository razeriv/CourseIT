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
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<String> error = new MutableLiveData<>();

    private final NewsRepository repository = new NewsRepository();

    public LiveData<List<Headline>> getNews() { return news; }
    public LiveData<Boolean> getIsLoading() { return isLoading; }
    public LiveData<String> getError() { return error; }

    public void loadNews() {
        isLoading.setValue(true);
        error.setValue(null);
        repository.getNews(new retrofit2.Callback<List<Headline>>() {
            @Override
            public void onResponse(@NonNull Call<List<Headline>> call, @NonNull Response<List<Headline>> response) {
                isLoading.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    news.setValue(response.body());
                } else {
                    error.setValue("Не удалось загрузить новости");
                    news.setValue(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Headline>> call, @NonNull Throwable t) {
                isLoading.setValue(false);
                error.setValue("Ошибка подключения: " + t.getMessage());
                news.setValue(new ArrayList<>());
            }
        });
    }
}