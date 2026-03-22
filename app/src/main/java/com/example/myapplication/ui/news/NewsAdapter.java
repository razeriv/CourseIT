package com.example.myapplication.ui.news;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private List<NewsItem> newsList;

    public NewsAdapter() {
        this.newsList = new ArrayList<>();
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        NewsItem currentNews = newsList.get(position);

        holder.newsTitle.setText(currentNews.getTitle());
        holder.newsDescription.setText(currentNews.getDescription());
        holder.newsDate.setText(currentNews.getDate());
        holder.newsImage.setImageResource(currentNews.getImageResource());
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<NewsItem> newList) {
        this.newsList.clear();
        this.newsList.addAll(newList);
        notifyDataSetChanged();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView newsTitle;
        TextView newsDescription;
        TextView newsDate;
        ImageView newsImage;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            newsTitle = itemView.findViewById(R.id.NewsTitle);
            newsDescription = itemView.findViewById(R.id.NewsDescription);
            newsDate = itemView.findViewById(R.id.NewsData);
            newsImage = itemView.findViewById(R.id.NewsImage);
        }
    }
}