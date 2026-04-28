package com.example.myapplication.ui.projects;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.Objects;

public class ProjectsAdapter extends ListAdapter<Project, ProjectsAdapter.ProjectViewHolder> {

    public static final int TYPE_HORIZONTAL = 0;
    public static final int TYPE_VERTICAL = 1;

    private final int viewType;
    private final OnProjectClickListener onProjectClickListener;

    public ProjectsAdapter(int viewType, OnProjectClickListener listener) {
        super(new ProjectDiffCallback());
        this.viewType = viewType;
        this.onProjectClickListener = listener;
    }

    @NonNull
    @Override
    public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutRes = (this.viewType == TYPE_HORIZONTAL)
                ? R.layout.item_project_horizontal
                : R.layout.item_project_vertical;

        View view = LayoutInflater.from(parent.getContext())
                .inflate(layoutRes, parent, false);

        return new ProjectViewHolder(view, onProjectClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectViewHolder holder, int position) {
        Project project = getItem(position);
        holder.bind(project);
    }

    public class ProjectViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvTitle;
        private final TextView tvDescription;
        private final TextView tvInstructor;
        private final TextView tvDeadline;
        private final TextView tvDifficulty;

        public ProjectViewHolder(@NonNull View itemView, OnProjectClickListener listener) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.ProjectTitle);
            tvDescription = itemView.findViewById(R.id.ProjectDescription);
            tvInstructor = itemView.findViewById(R.id.ProjectInstructor);
            tvDeadline = itemView.findViewById(R.id.ProjectDeadline);
            tvDifficulty = itemView.findViewById(R.id.ProjectDifficulty);

            itemView.setOnClickListener(v -> {
                int position = getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onProjectClick(getItem(position));
                }
            });
        }

        @SuppressLint("SetTextI18n")
        public void bind(Project project) {
            tvTitle.setText(safe(project.getTitle(), "Без названия"));
            tvDescription.setText(safe(project.getDescription(), "Нет описания"));
            tvInstructor.setText("Преподаватель: " + safe(project.getInstructor(), "Не указан"));
            tvDeadline.setText(safe(project.getDeadline(), "Нет дедлайна"));
            tvDifficulty.setText(safe(project.getDifficulty(), "-"));
        }

        private String safe(String value, String defaultValue) {
            return (value != null && !value.trim().isEmpty()) ? value : defaultValue;
        }
    }

    private static class ProjectDiffCallback extends DiffUtil.ItemCallback<Project> {

        @Override
        public boolean areItemsTheSame(@NonNull Project oldItem, @NonNull Project newItem) {
            return Objects.equals(oldItem.getTitle(), newItem.getTitle()) &&
                    Objects.equals(oldItem.getInstructor(), newItem.getInstructor());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Project oldItem, @NonNull Project newItem) {
            return Objects.equals(oldItem.getTitle(), newItem.getTitle()) &&
                    Objects.equals(oldItem.getDescription(), newItem.getDescription()) &&
                    Objects.equals(oldItem.getInstructor(), newItem.getInstructor()) &&
                    Objects.equals(oldItem.getDeadline(), newItem.getDeadline()) &&
                    Objects.equals(oldItem.getDifficulty(), newItem.getDifficulty()) &&
                    Objects.equals(oldItem.getRequirements(), newItem.getRequirements());
        }
    }

    public interface OnProjectClickListener {
        void onProjectClick(Project project);
    }
}