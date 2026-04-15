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
    private List<Project> projectList;
    private List<Project> projectListFull;
    private OnProjectClickListener listener;

    private final int viewType;

    public ProjectsAdapter(int viewType) {
        this.viewType = viewType;
        this.projectList = new ArrayList<>();
        this.projectListFull = new ArrayList<>();
    }

    @NonNull
    @Override
    public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int ignored) {

        View view;

        if (viewType == TYPE_HORIZONTAL) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_project_horizontal, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_project_vertical, parent, false);
        }

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
            boolean analytics,
            boolean ai,
            boolean db,
            String difficulty,
            String dateFrom,
            String dateTo
    ) {

        query = query.toLowerCase();
        projectList.clear();

        for (Project p : projectListFull) {

            boolean matchesSearch =
                    query.isEmpty()
                            || p.getTitle().toLowerCase().contains(query)
                            || p.getDescription().toLowerCase().contains(query)
                            || p.getInstructor().toLowerCase().contains(query);

            String topic = p.getTopic();

            boolean noTopicSelected =
                    !web && !admin && !android && !analytics && !ai && !db;

            boolean matchesTopic =
                    noTopicSelected
                            || (web && "web".equalsIgnoreCase(topic))
                            || (admin && "admin".equalsIgnoreCase(topic))
                            || (android && "android".equalsIgnoreCase(topic))
                            || (analytics && "analytics".equalsIgnoreCase(topic))
                            || (ai && "ai".equalsIgnoreCase(topic))
                            || (db && "db".equalsIgnoreCase(topic));

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

            if (matchesSearch && matchesTopic && matchesDifficulty && matchesDate) {
                projectList.add(p);
            }
        }

        notifyDataSetChanged();
    }

    public class ProjectViewHolder extends RecyclerView.ViewHolder {
        TextView ProjectTitle;
        TextView ProjectDescription;
        TextView ProjectInstructor;
        TextView ProjectDeadline;
        TextView ProjectDifficulty;
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

    private int convertDate(String date) {
        try {
            date = date.trim();
            String[] parts = date.split("\\.");

            if (parts.length != 2) return 0;

            int day = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);

            return month * 100 + day;
        } catch (Exception e) {
            return 0;
        }
    }
    public interface OnProjectClickListener {
        void onProjectClick(Project project);
    }

    public void setOnProjectClickListener(OnProjectClickListener listener) {
        this.listener = listener;
    }
}