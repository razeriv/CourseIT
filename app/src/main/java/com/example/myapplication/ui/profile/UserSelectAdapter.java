package com.example.myapplication.ui.profile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class UserSelectAdapter extends RecyclerView.Adapter<UserSelectAdapter.ViewHolder> {

    public interface OnUserClickListener {
        void onUserClick(UserListItem user);
    }

    private List<UserListItem> users = new ArrayList<>();
    private String selectedId = null;
    private final OnUserClickListener listener;

    public UserSelectAdapter(OnUserClickListener listener) {
        this.listener = listener;
    }

    public void setData(List<UserListItem> data) {
        users = data != null ? data : new ArrayList<>();
        notifyDataSetChanged();
    }

    public void setSelectedId(String id) {
        selectedId = id;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user_select, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserListItem user = users.get(position);
        holder.name.setText(user.getFullName());
        holder.subtitle.setText(user.getSubtitle());

        boolean selected = selectedId != null && selectedId.equals(user.getId());
        holder.indicator.setVisibility(selected ? View.VISIBLE : View.INVISIBLE);
        holder.check.setVisibility(selected ? View.VISIBLE : View.INVISIBLE);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onUserClick(user);
        });
    }

    @Override
    public int getItemCount() { return users.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, subtitle, check;
        View indicator;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name      = itemView.findViewById(R.id.userName);
            subtitle  = itemView.findViewById(R.id.userSubtitle);
            check     = itemView.findViewById(R.id.userSelectedCheck);
            indicator = itemView.findViewById(R.id.userSelectedIndicator);
        }
    }
}
