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

    private List<Project> projectList;
    private List<Project> projectListFull;
    private OnProjectClickListener listener;

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
        Project project = projectList.get(position);
        holder.ProjectTitle.setText(project.getTitle());
        holder.ProjectDescription.setText(project.getDescription());
        holder.ProjectInstructor.setText("Преподаватель: " + project.getInstructor());
    }

    @Override
    public int getItemCount() {
        return projectList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<Project> newList) {
        this.projectList = new ArrayList<>(newList);
        this.projectListFull = new ArrayList<>(newList);
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void filter(String query) {
        query = query.toLowerCase();
        projectList.clear();

        if (query.isEmpty()) {
            projectList.addAll(projectListFull);
        } else {
            for (Project project : projectListFull) {
                if (project.getTitle().toLowerCase().contains(query)) {
                    projectList.add(project);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class ProjectViewHolder extends RecyclerView.ViewHolder {
        TextView ProjectTitle;
        TextView ProjectDescription;
        TextView ProjectInstructor;

        public ProjectViewHolder(@NonNull View itemView) {
            super(itemView);
            ProjectTitle = itemView.findViewById(R.id.ProjectTitle);
            ProjectDescription = itemView.findViewById(R.id.ProjectDescription);
            ProjectInstructor = itemView.findViewById(R.id.ProjectInstructor);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onProjectClick(projectList.get(position));
                    }
                }
            });
        }
    }

    public interface OnProjectClickListener {
        void onProjectClick(Project project);
    }

    public void setOnProjectClickListener(OnProjectClickListener listener) {
        this.listener = listener;
    }
}