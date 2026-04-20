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
    private final ProjectsRepository repository = new ProjectsRepository();

    public LiveData<List<Project>> getProjects() {
        return projects;
    }

    public void loadProjects() {
        repository.getProjects(new retrofit2.Callback<List<Project>>() {
            @Override
            public void onResponse(@NonNull Call<List<Project>> call, @NonNull Response<List<Project>> response) {
                if (response.isSuccessful()) {
                    projects.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Project>> call, @NonNull Throwable t) {
                projects.setValue(new ArrayList<>());
            }
        });
    }
}
