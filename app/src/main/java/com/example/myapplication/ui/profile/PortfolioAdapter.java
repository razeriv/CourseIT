package com.example.myapplication.ui.profile;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class PortfolioAdapter extends RecyclerView.Adapter<PortfolioAdapter.ViewHolder> {

    private List<Portfolio> data = new ArrayList<>();

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<Portfolio> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_portfolio, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Portfolio item = data.get(position);

        holder.title.setText(item.getTitle());
        holder.description.setText(item.getDescription());
        holder.topic.setText("Тема:\n" + item.getTopic());
        holder.status.setText(item.getStatus() + "   |   ");
        holder.deadline.setText(item.getDeadline() + "   |   ");
        holder.difficulty.setText(item.getDifficulty());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, description, topic, status, deadline, difficulty;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.ProjectTitle);
            description = itemView.findViewById(R.id.ProjectDescription);
            topic = itemView.findViewById(R.id.ProjectTopic);
            status = itemView.findViewById(R.id.ProjectStatus);
            deadline = itemView.findViewById(R.id.ProjectDeadline);
            difficulty = itemView.findViewById(R.id.ProjectDifficulty);
        }
    }
}