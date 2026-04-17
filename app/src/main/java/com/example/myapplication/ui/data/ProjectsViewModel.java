package com.example.myapplication.ui.data;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.ui.projects.Project;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectsViewModel extends ViewModel {

    private final MutableLiveData<List<Project>> projects = new MutableLiveData<>();
    private final ProjectsRepository repository = new ProjectsRepository();

    public LiveData<List<Project>> getProjects() {
        return projects;
    }
    private boolean isLoading = false;

    public void loadProjects() {

        if (isLoading) return;

        if (projects.getValue() != null && !projects.getValue().isEmpty()) {
            return;
        }

        isLoading = true;

        repository.getProjects(new Callback<List<Project>>() {
            @Override
            public void onResponse(@NonNull Call<List<Project>> call,
                                   @NonNull Response<List<Project>> response) {

                isLoading = false;

                if (response.isSuccessful()) {
                    projects.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Project>> call,
                                  @NonNull Throwable t) {

                isLoading = false;

                Log.e("API", "FAIL: " + t.getMessage());
            }
        });
    }
}
