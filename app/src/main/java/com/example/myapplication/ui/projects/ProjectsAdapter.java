package com.example.myapplication.ui.projects;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class ProjectsAdapter extends RecyclerView.Adapter<ProjectsAdapter.ProjectViewHolder> {

    public static final int TYPE_HORIZONTAL = 0;
    public static final int TYPE_VERTICAL = 1;

    private List<Project> projectList = new ArrayList<>();
    private List<Project> projectListFull = new ArrayList<>();

    private OnProjectClickListener listener;
    private final int viewType;

    public ProjectsAdapter(int viewType) {
        this.viewType = viewType;
    }

    @NonNull
    @Override
    public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                this.viewType == TYPE_HORIZONTAL
                        ? R.layout.item_project_horizontal
                        : R.layout.item_project_vertical,
                parent, false);

        return new ProjectViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ProjectViewHolder holder, int position) {
        Project project = projectList.get(position);

        holder.ProjectTitle.setText(safe(project.getTitle(), "Без названия"));
        holder.ProjectDescription.setText(safe(project.getDescription(), "Нет описания"));
        holder.ProjectInstructor.setText("Преподаватель: " + safe(project.getInstructor(), "Не указан"));
        holder.ProjectDeadline.setText(safe(project.getDeadline(), "Нет дедлайна"));
        holder.ProjectDifficulty.setText(safe(project.getDifficulty(), "-"));
    }

    private String safe(String value, String defaultValue) {
        return value != null && !value.trim().isEmpty() ? value : defaultValue;
    }

    @Override
    public int getItemCount() {
        return projectList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<Project> newList) {
        projectListFull = newList != null ? new ArrayList<>(newList) : new ArrayList<>();
        projectList = new ArrayList<>(projectListFull);
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void applyFilters(String query,
                             boolean web, boolean admin, boolean android,
                             boolean analytics, boolean ai, boolean db,
                             String difficulty,
                             String dateFrom, String dateTo) {

        query = query.toLowerCase().trim();
        projectList.clear();

        for (Project p : projectListFull) {
            boolean matchesSearch = query.isEmpty() ||
                    safe(p.getTitle(), "").toLowerCase().contains(query) ||
                    safe(p.getDescription(), "").toLowerCase().contains(query) ||
                    safe(p.getInstructor(), "").toLowerCase().contains(query);

            boolean matchesTopic = isTopicMatch(p.getTopic(), web, admin, android, analytics, ai, db);
            boolean matchesDifficulty = difficulty.isEmpty() ||
                    difficulty.equalsIgnoreCase(safe(p.getDifficulty(), ""));
            boolean matchesDate = isDateInRange(p.getDeadline(), dateFrom, dateTo);

            if (matchesSearch && matchesTopic && matchesDifficulty && matchesDate) {
                projectList.add(p);
            }
        }
        notifyDataSetChanged();
    }

    private boolean isTopicMatch(String topic, boolean web, boolean admin, boolean android,
                                 boolean analytics, boolean ai, boolean db) {
        if (!web && !admin && !android && !analytics && !ai && !db) return true;

        topic = safe(topic, "").toLowerCase();
        return (web && topic.contains("веб")) ||
                (admin && topic.contains("админ")) ||
                (android && topic.contains("мобильн")) ||
                (analytics && topic.contains("data")) ||
                (ai && (topic.contains("ai") || topic.contains("искусственный"))) ||
                (db && topic.contains("баз"));
    }

    private boolean isDateInRange(String deadline, String dateFrom, String dateTo) {
        if (deadline == null || (!dateFrom.isEmpty() || !dateTo.isEmpty())) {
            return true;
        }
        return true;
    }

    public interface OnProjectClickListener {
        void onProjectClick(Project project);
    }

    public void setOnProjectClickListener(OnProjectClickListener listener) {
        this.listener = listener;
    }

    public class ProjectViewHolder extends RecyclerView.ViewHolder {
        TextView ProjectTitle, ProjectDescription, ProjectInstructor, ProjectDeadline, ProjectDifficulty;
        CardView cardView;

        public ProjectViewHolder(@NonNull View itemView) {
            super(itemView);
            ProjectTitle = itemView.findViewById(R.id.ProjectTitle);
            ProjectDescription = itemView.findViewById(R.id.ProjectDescription);
            ProjectInstructor = itemView.findViewById(R.id.ProjectInstructor);
            ProjectDeadline = itemView.findViewById(R.id.ProjectDeadline);
            ProjectDifficulty = itemView.findViewById(R.id.ProjectDifficulty);
            cardView = itemView.findViewById(R.id.cardView);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onProjectClick(projectList.get(position));
                }
            });
        }
    }
}