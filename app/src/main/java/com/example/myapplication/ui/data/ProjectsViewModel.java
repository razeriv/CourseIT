package com.example.myapplication.ui.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.ui.projects.Project;

import java.util.List;

public class ProjectsViewModel extends ViewModel {

    private final MutableLiveData<List<Project>> projects = new MutableLiveData<>();

    public void setProjects(List<Project> list) {
        projects.setValue(list);
    }

    public LiveData<List<Project>> getProjects() {
        return projects;
    }
}
