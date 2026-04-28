package com.example.myapplication.ui.data;

import com.example.myapplication.ui.network.RetrofitClient;
import com.example.myapplication.ui.projects.CreateProjectRequest;
import com.example.myapplication.ui.projects.Project;

import java.util.List;

import retrofit2.Callback;

public class ProjectsRepository {

    public void getProjects(Callback<List<Project>> callback) {
        RetrofitClient.getApi().getProjects().enqueue(callback);
    }

    public void createProject(CreateProjectRequest request, Callback<Project> callback) {
        RetrofitClient.getApi().createProject(request).enqueue(callback);
    }
}