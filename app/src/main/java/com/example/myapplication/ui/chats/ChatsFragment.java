package com.example.myapplication.ui.chats;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.FragmentChatsBinding;

import java.util.ArrayList;
import java.util.List;

public class ChatsFragment extends Fragment {

    private FragmentChatsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentChatsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView = binding.recyclerChats;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<ChatItem> chatList = new ArrayList<>();
        chatList.add(new ChatItem("Иван Иванов", "Проект 1", "Преподаватель увидел отклик", 0));
        chatList.add(new ChatItem("Петр Петров", "Проект 2", "Ответьте на сообщение", 1));
        chatList.add(new ChatItem("Анна Смирнова", "Очень длинное название проекта...", "Проект выполнен", 0));

        ChatsAdapter adapter = new ChatsAdapter(chatList, chat -> {
            Intent intent = new Intent(requireContext(), ChatActivity.class);
            intent.putExtra("name", chat.name);
            intent.putExtra("project", chat.project);
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}