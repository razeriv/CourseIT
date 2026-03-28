package com.example.myapplication.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;
import java.util.List;

public class RecommendationAdapter extends RecyclerView.Adapter<RecommendationAdapter.ViewHolder> {

    private final List<String[]> data;

    public RecommendationAdapter(List<String[]> data) {
        this.data = data;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView projectName;
        TextView complexity;
        TextView duration;
        TextView details;
        TextView teacher;

        public ViewHolder(View itemView) {
            super(itemView);
            projectName = itemView.findViewById(R.id.project_name);
            complexity = itemView.findViewById(R.id.complexity);
            duration = itemView.findViewById(R.id.duration);
            details = itemView.findViewById(R.id.details);
            teacher = itemView.findViewById(R.id.teacher);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_recommendation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String[] currentProject = data.get(position);

        holder.projectName.setText(currentProject[0]);
        holder.complexity.setText(currentProject[1]);
        holder.duration.setText(currentProject[2]);
        holder.details.setText(currentProject[3]);
        holder.teacher.setText(currentProject[4]);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}