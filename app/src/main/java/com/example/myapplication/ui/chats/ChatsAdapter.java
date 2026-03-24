package com.example.myapplication.ui.chats;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import java.util.List;

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ChatViewHolder> {

    private List<ChatItem> chats;

    public ChatsAdapter(List<ChatItem> chats) {
        this.chats = chats;
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView name, project, message, status;

        public ChatViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            project = view.findViewById(R.id.project);
            message = view.findViewById(R.id.message);
            status = view.findViewById(R.id.status);
        }
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chat, parent, false);
        return new ChatViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        ChatItem chat = chats.get(position);

        holder.name.setText(chat.name);
        holder.project.setText(chat.project);
        holder.message.setText(chat.lastMessage);

        if (chat.unreadCount > 0) {
            holder.status.setText(String.valueOf(chat.unreadCount));
            holder.status.setVisibility(View.VISIBLE);
        } else {
            holder.status.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(v -> {
            Log.d("CHAT", "Clicked: " + chat.name);
        });
    }

    @Override
    public int getItemCount() {
        return chats != null ? chats.size() : 0;
    }
}
