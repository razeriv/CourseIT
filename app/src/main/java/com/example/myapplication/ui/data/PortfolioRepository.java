package com.example.myapplication.ui.data;

import com.example.myapplication.ui.profile.Portfolio;

import java.util.ArrayList;
import java.util.List;

public class PortfolioRepository {

    public List<Portfolio> getPortfolio() {
        List<Portfolio> portfolio = new ArrayList<>();

        portfolio.add(new Portfolio(
                "Исследование нейронных сетей",
                "подробности",
                "UI/UX",
                "завершен",
                "23.03 - 27.03",
                "легкий"
        ));

        portfolio.add(new Portfolio(
                "Мобильное приложение",
                "подробности",
                "Android",
                "в процессе",
                "01.04 - 10.04",
                "средний"
        ));

        portfolio.add(new Portfolio(
                "Веб-сервис",
                "подробности",
                "Backend",
                "завершен",
                "12.02 - 20.02",
                "сложный"
        ));

        portfolio.add(new Portfolio(
                "Дизайн интерфейса",
                "подробности",
                "Figma",
                "завершен",
                "05.03 - 15.03",
                "легкий"
        ));

        return portfolio;
    }
}