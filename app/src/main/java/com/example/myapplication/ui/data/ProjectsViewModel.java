package com.example.myapplication.ui.data;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.ui.projects.Project;

import java.util.ArrayList;
import java.util.List;

public class ProjectsViewModel extends ViewModel {

    private final MutableLiveData<List<Project>> allProjects = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<List<Project>> filteredProjects = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    private final ProjectsRepository repository = new ProjectsRepository();

    // Публичные LiveData
    public LiveData<List<Project>> getProjects() {
        return filteredProjects;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void loadProjects() {
        isLoading.setValue(true);

        repository.getProjects(new retrofit2.Callback<List<Project>>() {
            @Override
            public void onResponse(@NonNull retrofit2.Call<List<Project>> call,
                                   @NonNull retrofit2.Response<List<Project>> response) {
                isLoading.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    List<Project> projectsList = response.body();
                    allProjects.setValue(projectsList);
                    filteredProjects.setValue(projectsList); // изначально показываем все
                } else {
                    List<Project> empty = new ArrayList<>();
                    allProjects.setValue(empty);
                    filteredProjects.setValue(empty);
                }
            }

            @Override
            public void onFailure(@NonNull retrofit2.Call<List<Project>> call,
                                  @NonNull Throwable t) {
                isLoading.setValue(false);
                t.printStackTrace();
                List<Project> empty = new ArrayList<>();
                allProjects.setValue(empty);
                filteredProjects.setValue(empty);
            }
        });
    }

    public void applyFilters(String query,
                             boolean web,
                             boolean admin,
                             boolean android,
                             boolean analytics,
                             boolean ai,
                             boolean db,
                             String difficulty,
                             String dateFrom,
                             String dateTo) {

        List<Project> source = allProjects.getValue();
        if (source == null || source.isEmpty()) {
            filteredProjects.setValue(new ArrayList<>());
            return;
        }

        query = (query == null ? "" : query.trim().toLowerCase());

        List<Project> result = new ArrayList<>();

        for (Project p : source) {
            if (p == null) continue;

            boolean matchesSearch = query.isEmpty() ||
                    containsIgnoreCase(p.getTitle(), query) ||
                    containsIgnoreCase(p.getDescription(), query) ||
                    containsIgnoreCase(p.getInstructor(), query);

            boolean matchesTopic = isTopicMatch(p.getTopic(), web, admin, android, analytics, ai, db);

            boolean matchesDifficulty = difficulty.isEmpty() ||
                    difficulty.equalsIgnoreCase(p.getDifficulty());

            boolean matchesDate = isDateInRange(p.getDeadline(), dateFrom, dateTo);

            if (matchesSearch && matchesTopic && matchesDifficulty && matchesDate) {
                result.add(p);
            }
        }

        filteredProjects.setValue(result);
    }

    private boolean containsIgnoreCase(String text, String query) {
        if (text == null) return false;
        return text.toLowerCase().contains(query);
    }

    private boolean isTopicMatch(String topic, boolean web, boolean admin, boolean android,
                                 boolean analytics, boolean ai, boolean db) {

        if (!web && !admin && !android && !analytics && !ai && !db) {
            return true; // все темы включены
        }

        if (topic == null) return false;
        String t = topic.toLowerCase();

        return (web && t.contains("веб")) ||
                (admin && t.contains("админ")) ||
                (android && (t.contains("android") || t.contains("мобильн"))) ||
                (analytics && t.contains("data")) ||
                (ai && (t.contains("ai") || t.contains("искусственный"))) ||
                (db && t.contains("баз"));
    }

    private boolean isDateInRange(String deadline, String dateFrom, String dateTo) {
        if (deadline == null || (dateFrom.isEmpty() && dateTo.isEmpty())) {
            return true;
        }
        // TODO: Реализовать полноценную проверку дат при необходимости
        return true;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}