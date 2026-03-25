package com.example.myapplication.ui.chats;

import android.os.Bundle;
import android.os.Handler;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MessageAdapter adapter;
    private List<Message> messages;

    private EditText editMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_messages);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        TextView chatName = findViewById(R.id.chatName);
        TextView chatStatus = findViewById(R.id.chatStatus);
        ImageView backButton = findViewById(R.id.backButton);

        String name = getIntent().getStringExtra("name");
        if (name != null) {
            chatName.setText(name);
        }

        chatStatus.setText("в сети • сейчас");

        backButton.setOnClickListener(v -> onBackPressed());

        recyclerView = findViewById(R.id.recyclerMessages);
        editMessage = findViewById(R.id.editMessage);
        ImageView sendButton = findViewById(R.id.sendButton);

        messages = new ArrayList<>();

        messages.add(new Message("Привет!", false));
        messages.add(new Message("Здравствуйте 👋", true));

        adapter = new MessageAdapter(messages);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);

        recyclerView.scrollToPosition(messages.size() - 1);

        sendButton.setOnClickListener(v -> sendMessage());
    }

    private void sendMessage() {
        String text = editMessage.getText().toString().trim();

        if (text.isEmpty()) return;

        messages.add(new Message(text, true));
        adapter.notifyItemInserted(messages.size() - 1);

        editMessage.setText("");
        recyclerView.scrollToPosition(messages.size() - 1);

        simulateReply();
    }

    private void simulateReply() {
        new Handler().postDelayed(() -> {
            messages.add(new Message("Ок, понял 👍", false));
            adapter.notifyItemInserted(messages.size() - 1);
            recyclerView.scrollToPosition(messages.size() - 1);
        }, 1500);
    }
}