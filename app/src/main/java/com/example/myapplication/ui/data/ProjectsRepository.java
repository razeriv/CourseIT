package com.example.myapplication.ui.data;

import com.example.myapplication.ui.projects.Project;
import com.example.myapplication.ui.projects.Topic;

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
                    Topic.ANDROID,
                    "средний",
                    "01.04 - 15.05",
                    "Иметь 2 миллиона долларов и сумку гуччи"
            ));

            projects.add(new Project(
                    "Корпоративный сайт",
                    "Редизайн",
                    "Figma + адаптив",
                    "Петров П.П.",
                    Topic.WEB,
                    "лёгкий",
                    "10.03 - 01.04",
                    "Иметь полмиллиона долларов и сумку прада"
            ));

            projects.add(new Project(
                    "Серверная инфраструктура",
                    "Настройка Linux",
                    "Docker + nginx",
                    "Сидоров С.С.",
                    Topic.ADMIN,
                    "сложный",
                    "15.05 - 30.06",
                    "Иметь 5 миллионов долларов и сумку версачи"
            ));

            projects.add(new Project(
                    "Анализ данных",
                    "Обработка статистики",
                    "Python + Pandas",
                    "Кузнецов К.К.",
                    Topic.ANALYTICS,
                    "средний",
                    "05.04 - 20.05",
                    "Знание статистики"
            ));

            projects.add(new Project(
                    "AI помощник",
                    "Чат-бот",
                    "Machine Learning",
                    "Смирнов А.А.",
                    Topic.AI,
                    "сложный",
                    "01.06 - 15.07",
                    "Знание ML"
            ));

            projects.add(new Project(
                    "База данных магазина",
                    "Проектирование БД",
                    "PostgreSQL",
                    "Орлов О.О.",
                    Topic.DB,
                    "лёгкий",
                    "10.04 - 25.04",
                    "SQL базовый уровень"
            ));
        }

        return projects;
    }
}