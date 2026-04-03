package com.example.myapplication.ui.data;

import com.example.myapplication.R;
import com.example.myapplication.ui.news.Headline;

import java.util.ArrayList;
import java.util.List;

public class NewsRepository {
    private static List<Headline> news;

    public static List<Headline> getNews() {

        if (news == null) {
            news = new ArrayList<>();

            news.add(new Headline(
                "Новость 1",
                    "Описание для первой новости",
                    "02.04.2026",
                    R.drawable.avatar_1
            ));

            news.add(new Headline(
                    "Новость 2",
                    "Описание для второй новости",
                    "30.03.2026",
                    R.drawable.avatar_2
            ));

            news.add(new Headline(
                    "Новость 3",
                    "Описание для третьей новости",
                    "27.03.2026",
                    R.drawable.avatar_3
            ));
        }

        return news;
    }
}
