package com.example.myapplication.ui.chats;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.databinding.FragmentMessagesBinding;

import java.util.ArrayList;
import java.util.List;

public class MessagesFragment extends Fragment {

    private FragmentMessagesBinding binding;
    private final List<Message> messageList = new ArrayList<>();
    private MessageAdapter adapter;

    @SuppressLint("NotifyDataSetChanged")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMessagesBinding.inflate(inflater, container, false);

        adapter = new MessageAdapter(messageList);
        binding.recyclerMessages.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true)
        );
        binding.recyclerMessages.setAdapter(adapter);

        messageList.add(new Message("Привет", false));
        messageList.add(new Message("Здравствуйте!", true));

        binding.recyclerMessages.scrollToPosition(0);

        binding.backButton.setOnClickListener(v -> {
            requireActivity().onBackPressed();
        });

        adapter.notifyDataSetChanged();

        binding.sendButton.setOnClickListener(v -> {
            String text = binding.editMessage.getText().toString();
            if (!text.isEmpty()) {
                messageList.add(new Message(text, true));
                adapter.notifyItemInserted(messageList.size() - 1);
                binding.editMessage.setText("");
                binding.recyclerMessages.scrollToPosition(messageList.size() - 1);
            }
        });

        return binding.getRoot();
    }
}
