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

    public LiveData<List<Project>> getProjects() {
        return filteredProjects;
    }

    private final MutableLiveData<String> error = new MutableLiveData<>();

    public LiveData<Boolean> getIsLoading() { return isLoading; }
    public LiveData<String> getError() { return error; }

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
                    filteredProjects.setValue(projectsList);
                } else {
                    error.setValue("Не удалось загрузить проекты");
                    List<Project> empty = new ArrayList<>();
                    allProjects.setValue(empty);
                    filteredProjects.setValue(empty);
                }
            }

            @Override
            public void onFailure(@NonNull retrofit2.Call<List<Project>> call,
                                  @NonNull Throwable t) {
                isLoading.setValue(false);
                error.setValue("Ошибка подключения: " + t.getMessage());
                t.printStackTrace();
                List<Project> empty = new ArrayList<>();
                allProjects.setValue(empty);
                filteredProjects.setValue(empty);
            }
        });
    }


    public void applyFilters(String query, boolean web, boolean admin, boolean android,
                              boolean analytics, boolean ai, boolean db,
                              String difficulty, String dateFrom, String dateTo) {
        List<Project> source = allProjects.getValue();
        if (source == null) {
            filteredProjects.setValue(new ArrayList<>());
            return;
        }

        String lowerQuery = query == null ? "" : query.toLowerCase().trim();

        List<Project> result = new ArrayList<>();
        for (Project p : source) {
            // Поиск по тексту
            if (!lowerQuery.isEmpty()) {
                boolean matchesQuery = containsIgnoreCase(p.getTitle(), lowerQuery)
                        || containsIgnoreCase(p.getDetails(), lowerQuery)
                        || containsIgnoreCase(p.getInstructor(), lowerQuery);
                if (!matchesQuery) continue;
            }

            // Фильтр по теме
            boolean topicMatches = isTopicMatch(p.getTitle(), web, admin, android, analytics, ai, db)
                    || isTopicMatch(p.getDetails(), web, admin, android, analytics, ai, db);
            if (!topicMatches) continue;

            // Фильтр по сложности
            if (!difficulty.isEmpty() && !difficulty.equalsIgnoreCase(p.getDifficulty())) {
                continue;
            }

            // Фильтр по дате
            if (!isDateInRange(p.getDeadline(), dateFrom, dateTo)) {
                continue;
            }

            result.add(p);
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
            return true;
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
        if (deadline == null || deadline.isEmpty()) return true;
        if (dateFrom.isEmpty() && dateTo.isEmpty()) return true;

        try {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd.MM.yyyy", java.util.Locale.getDefault());
            sdf.setLenient(false);
            java.util.Date deadlineDate = sdf.parse(deadline.trim());
            if (deadlineDate == null) return true;

            if (!dateFrom.isEmpty()) {
                java.util.Date from = sdf.parse(dateFrom.trim());
                if (from != null && deadlineDate.before(from)) return false;
            }

            if (!dateTo.isEmpty()) {
                java.util.Date to = sdf.parse(dateTo.trim());
                if (to != null && deadlineDate.after(to)) return false;
            }

            return true;
        } catch (java.text.ParseException e) {
            return true;
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}