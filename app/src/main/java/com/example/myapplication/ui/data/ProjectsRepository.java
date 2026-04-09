package com.example.myapplication.ui.data;

import com.example.myapplication.ui.projects.Project;
import java.util.ArrayList;
import java.util.List;

public class ProjectsRepository {

    private static List<Project> projects;

    public static List<Project> getProjects() {

        if (projects == null) {
            projects = new ArrayList<>();

            projects.add(new Project(
                    "Финансовое приложение",
                    "Учет расходов",
                    "Интеграция API банка",
                    "Иванов И.И.",
                    "android",
                    "средний",
                    "01.04 - 15.05",
                    "Иметь 2 миллиона долларов и сумку гуччи"
            ));

            projects.add(new Project(
                    "Корпоративный сайт",
                    "Редизайн",
                    "Figma + адаптив",
                    "Петров П.П.",
                    "web",
                    "лёгкий",
                    "10.03 - 01.04",
                    "Иметь полмиллиона долларов и сумку прада"
            ));

            projects.add(new Project(
                    "Серверная инфраструктура",
                    "Настройка Linux",
                    "Docker + nginx",
                    "Сидоров С.С.",
                    "admin",
                    "сложный",
                    "15.05 - 30.06",
                    "Иметь 5 миллионов долларов и сумку версачи"
            ));
        }

        return projects;
    }
}