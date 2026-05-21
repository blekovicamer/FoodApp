package com.example.pitica;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton; // 1. Added Import
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

public class ConversationsActivity extends AppCompatActivity {

    private RecyclerView rvConversations;
    private ConversationsAdapter adapter;
    private List<Chat> chatList;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversations);

        db = FirebaseFirestore.getInstance();
        rvConversations = findViewById(R.id.rvConversations);
        chatList = new ArrayList<>();

        adapter = new ConversationsAdapter(chatList);
        rvConversations.setLayoutManager(new LinearLayoutManager(this));
        rvConversations.setAdapter(adapter);

        // 2. Setup the "New Chat" button click listener
        FloatingActionButton fabNewChat = findViewById(R.id.fabNewChat);
        fabNewChat.setOnClickListener(v -> {
            Intent intent = new Intent(ConversationsActivity.this, SearchUsersActivity.class);
            startActivity(intent);
        });

        loadConversations();
    }

    private void loadConversations() {
        String currentUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        db.collection("chats")
                .whereArrayContains("participants", currentUid)
                .addSnapshotListener((value, error) -> {
                    if (value != null) {
                        chatList.clear();
                        for (QueryDocumentSnapshot doc : value) {
                            Chat chat = doc.toObject(Chat.class);
                            chatList.add(chat);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }
}