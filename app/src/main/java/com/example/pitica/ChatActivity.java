package com.example.pitica;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.SetOptions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView rvMessages;
    private ChatAdapter adapter;
    private List<Message> messageList;
    private EditText etMessage;
    private Button btnSend;
    private TextView tvChatTitle;

    private FirebaseFirestore db;
    private String currentUserId;
    private String chatId;
    private String friendUid;
    private String friendNickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Get Views
        tvChatTitle = findViewById(R.id.tvChatTitle);
        rvMessages = findViewById(R.id.rvMessages);
        etMessage = findViewById(R.id.etMessage);
        btnSend = findViewById(R.id.btnSend);

        // Get Intent Data
        friendUid = getIntent().getStringExtra("friendUid");
        friendNickname = getIntent().getStringExtra("friendNickname");
        String passedChatId = getIntent().getStringExtra("chatId");

        db = FirebaseFirestore.getInstance();
        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Generate Chat ID
        if (passedChatId != null) {
            chatId = passedChatId;
        } else if (friendUid != null) {
            chatId = generateChatId(currentUserId, friendUid);
        } else {
            finish();
            return;
        }

        // Logic for display name (Burek/Ninja generator)
        String seed = (friendUid != null) ? friendUid : chatId;
        String displayName = (friendNickname != null && !friendNickname.isEmpty())
                ? friendNickname
                : generateNickname(seed);

        // SET THE TEXT HERE
        tvChatTitle.setText(displayName);
        tvChatTitle.setTextColor(Color.WHITE);

        // Setup RecyclerView
        messageList = new ArrayList<>();
        adapter = new ChatAdapter(messageList);
        rvMessages.setLayoutManager(new LinearLayoutManager(this));
        rvMessages.setAdapter(adapter);

        loadMessages();

        btnSend.setOnClickListener(v -> {
            String text = etMessage.getText().toString().trim();
            if (!text.isEmpty()) {
                sendMessage(text, displayName);
                etMessage.setText("");
            }
        });
    }

    private String generateNickname(String seed) {
        String[] prefixes = {"Burek", "Sirnica", "Cat", "Ninja", "Chef", "Eagle", "Lion", "Wolf", "Ghost"};
        int hash = Math.abs(seed.hashCode());
        return prefixes[hash % prefixes.length] + (hash % 999);
    }

    private String generateChatId(String uid1, String uid2) {
        return (uid1.compareTo(uid2) < 0) ? uid1 + "_" + uid2 : uid2 + "_" + uid1;
    }

    private void sendMessage(String text, String displayName) {
        long timestamp = System.currentTimeMillis();
        Message message = new Message(currentUserId, text, timestamp);
        db.collection("chats").document(chatId).collection("messages").add(message);

        Map<String, Object> chatSummary = new HashMap<>();
        chatSummary.put("lastMessage", text);
        chatSummary.put("lastTimestamp", timestamp);
        chatSummary.put("chatId", chatId);
        if (friendUid != null)
            chatSummary.put("participants", Arrays.asList(currentUserId, friendUid));
        chatSummary.put("receiverName", displayName); // Save the name

        db.collection("chats").document(chatId).set(chatSummary, SetOptions.merge());
    }

    private void loadMessages() {
        db.collection("chats").document(chatId).collection("messages")
                .orderBy("timestamp", Query.Direction.ASCENDING)
                .addSnapshotListener((value, error) -> {
                    if (value != null) {
                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                Message message = dc.getDocument().toObject(Message.class);
                                messageList.add(message);
                                adapter.notifyItemInserted(messageList.size() - 1);
                                rvMessages.scrollToPosition(messageList.size() - 1);
                            }
                        }
                    }
                });
    }
}