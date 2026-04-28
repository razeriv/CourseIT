package com.example.myapplication.ui.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.ui.profile.Portfolio;
import com.example.myapplication.ui.projects.Project;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PortfolioViewModel extends ViewModel {

    private final MutableLiveData<List<Portfolio>> portfolioLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<String> error = new MutableLiveData<>();

    private final ProjectsRepository repository = new ProjectsRepository(); // или PortfolioRepository

    public LiveData<List<Portfolio>> getPortfolio() {
        return portfolioLiveData;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getError() {
        return error;
    }
    public void loadPortfolio() {
        isLoading.setValue(true);
        error.setValue(null);

        repository.getProjects(new Callback<List<Project>>() {
            @Override
            public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {
                isLoading.setValue(false);

                if (response.isSuccessful() && response.body() != null) {
                    portfolioLiveData.setValue(convertToPortfolio(response.body()));
                } else {
                    error.setValue("Не удалось загрузить портфолио");
                    portfolioLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<Project>> call, Throwable t) {
                isLoading.setValue(false);
                error.setValue("Ошибка подключения: " + t.getMessage());
                t.printStackTrace();
                portfolioLiveData.setValue(null);
            }
        });
    }

    private List<Portfolio> convertToPortfolio(List<Project> projects) {
        return null; // TODO: реализовать конвертацию
    }

    public void refresh() {
        loadPortfolio();
    }
}