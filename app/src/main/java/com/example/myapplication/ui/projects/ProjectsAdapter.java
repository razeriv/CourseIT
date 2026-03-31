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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ProjectViewHolder holder, int position) {
        Project project = projectList.get(position);
        holder.ProjectTitle.setText(project.getTitle());
        holder.ProjectDescription.setText(project.getDescription());
        holder.ProjectInstructor.setText("Преподаватель: " + project.getInstructor());
        holder.ProjectDeadline.setText(project.getDeadline());
        holder.ProjectDifficulty.setText(project.getDifficulty());
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
    public void applyFilters(
            String query,
            boolean web,
            boolean admin,
            boolean android,
            String difficulty,
            String dateFrom,
            String dateTo
    ) {

        query = query.toLowerCase();

        projectList.clear();

        for (Project p : projectListFull) {

            boolean matchesSearch =
                    query.isEmpty()
                            || p.getTitle().toLowerCase().contains(query);

            boolean matchesTopic =
                    (!web && !admin && !android)
                            || (web && p.getTopic().equalsIgnoreCase("web"))
                            || (admin && p.getTopic().equalsIgnoreCase("admin"))
                            || (android && p.getTopic().equalsIgnoreCase("android"));

            boolean matchesDifficulty =
                    difficulty.isEmpty()
                            || p.getDifficulty().equalsIgnoreCase(difficulty);

            boolean matchesDate = true;

            if (!dateFrom.isEmpty() || !dateTo.isEmpty()) {

                String[] dates = p.getDeadline().split(" - ");

                if (dates.length != 2)
                    continue;

                int projectStart = convertDate(dates[0]);
                int projectEnd = convertDate(dates[1]);

                int filterStart = dateFrom.isEmpty() ? 0 : convertDate(dateFrom);
                int filterEnd = dateTo.isEmpty() ? 1231 : convertDate(dateTo);

                matchesDate =
                        projectEnd >= filterStart &&
                                projectStart <= filterEnd;
            }

            if (matchesSearch && matchesTopic && matchesDifficulty && matchesDate)
                projectList.add(p);
        }

        notifyDataSetChanged();
    }
    public class ProjectViewHolder extends RecyclerView.ViewHolder {
        TextView ProjectTitle;
        TextView ProjectDescription;
        TextView ProjectInstructor;
        TextView ProjectDeadline;
        TextView ProjectDifficulty;

        public ProjectViewHolder(@NonNull View itemView) {
            super(itemView);
            ProjectTitle = itemView.findViewById(R.id.ProjectTitle);
            ProjectDescription = itemView.findViewById(R.id.ProjectDescription);
            ProjectInstructor = itemView.findViewById(R.id.ProjectInstructor);
            ProjectDeadline = itemView.findViewById(R.id.ProjectDeadline);
            ProjectDifficulty = itemView.findViewById(R.id.ProjectDifficulty);

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

    private int convertDate(String date) {
        date = date.trim();
        String[] parts = date.split("\\.");

        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);

        return month * 100 + day;
    }
    public interface OnProjectClickListener {
        void onProjectClick(Project project);
    }

    public void setOnProjectClickListener(OnProjectClickListener listener) {
        this.listener = listener;
    }
}