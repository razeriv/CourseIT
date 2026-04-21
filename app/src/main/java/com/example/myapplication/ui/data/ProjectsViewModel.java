package com.example.myapplication.ui.data;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.ui.projects.Project;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class ProjectsViewModel extends ViewModel {

    private final MutableLiveData<List<Project>> projects = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final ProjectsRepository repository = new ProjectsRepository();

    public LiveData<List<Project>> getProjects() {
        return projects;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void loadProjects() {
        isLoading.setValue(true);

        repository.getProjects(new retrofit2.Callback<List<Project>>() {
            @Override
            public void onResponse(@NonNull Call<List<Project>> call,
                                   @NonNull Response<List<Project>> response) {
                isLoading.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    projects.setValue(response.body());
                } else {
                    projects.setValue(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Project>> call,
                                  @NonNull Throwable t) {
                isLoading.setValue(false);
                t.printStackTrace();
                projects.setValue(new ArrayList<>());
            }
        });
    }
}