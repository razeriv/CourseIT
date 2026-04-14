package com.example.myapplication.ui.network;

import com.example.myapplication.ui.projects.Project;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("projects")
    Call<List<Project>> getProjects();
}
