package com.example.myapplication.ui.projects;
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

public class ProjectsAdapter extends RecyclerView.Adapter<ProjectsAdapter.ProjectViewHolder> {

    private List<String> projectList;
    private List<String> projectListFull;

    public ProjectsAdapter() {
        this.projectList = new ArrayList<>();
        this.projectListFull = new ArrayList<>();
    }

    @NonNull
    @Override
    public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_project, parent, false);
        return new ProjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectViewHolder holder, int position) {
        String projectTitle = projectList.get(position);
        holder.ProjectTitle.setText(projectTitle);
        holder.ProjectDescription.setText("Описание для " + projectTitle);
        holder.ProjectInstructor.setText("Преподаватель: Хуесос Хуесосович");
    }

    @Override
    public int getItemCount() {
        return projectList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<String> newList) {
        this.projectList = new ArrayList<>(newList);
        this.projectListFull = new ArrayList<>(newList);
        notifyDataSetChanged();
    }

    public void filter(String query) {
        query = query.toLowerCase();
        projectList.clear();

        if (query.isEmpty()) {
            projectList.addAll(projectListFull);
        } else {
            for (String project : projectListFull) {
                if (project.toLowerCase().contains(query)) {
                    projectList.add(project);
                }
            }
        }
        notifyDataSetChanged();
    }

    public static class ProjectViewHolder extends RecyclerView.ViewHolder {
        TextView ProjectTitle;
        TextView ProjectDescription;
        TextView ProjectInstructor;

        public ProjectViewHolder(@NonNull View itemView) {
            super(itemView);
            ProjectTitle = itemView.findViewById(R.id.ProjectTitle);
            ProjectDescription = itemView.findViewById(R.id.ProjectDescription);
            ProjectInstructor = itemView.findViewById(R.id.ProjectInstructor);
        }
    }
}
