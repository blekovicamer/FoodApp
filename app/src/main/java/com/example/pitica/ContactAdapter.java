package com.example.pitica;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    private List<Chat> chatList;

    public ContactAdapter(List<Chat> chatList) {
        this.chatList = chatList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Ensure this layout file matches the one used in your project
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_conversation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Chat chat = chatList.get(position);

        // 1. Get the name from the database
        String dbName = chat.getReceiverName();

        // 2. Logic: Only use the DB name if it's NOT null, NOT empty, and NOT "Chat"
        boolean isNameValid = dbName != null && !dbName.isEmpty() && !dbName.equals("Chat");

        // 3. Decide the name: Use DB name if valid, otherwise generate random nickname
        String name = isNameValid
                ? dbName
                : generateNickname(chat.getChatId() != null ? chat.getChatId() : "default");

        // 4. Set the UI
        holder.tvTitle.setText(name);
        holder.tvTitle.setTextColor(Color.WHITE);
        holder.tvSubtitle.setText(chat.getLastMessage() != null ? chat.getLastMessage() : "");

        // 5. Click listener
        holder.itemView.setOnClickListener(v -> {
            if (chat.getChatId() != null) {
                Intent intent = new Intent(v.getContext(), ChatActivity.class);
                intent.putExtra("chatId", chat.getChatId());
                intent.putExtra("friendNickname", name); // Pass the generated or saved name
                v.getContext().startActivity(intent);
            }
        });
    }

    // Helper method to generate the Burek/Ninja/etc nicknames
    private String generateNickname(String seed) {
        String[] prefixes = {"Burek", "Sirnica", "Cat", "Ninja", "Chef", "Eagle", "Lion", "Wolf", "Ghost"};
        int hash = Math.abs(seed.hashCode());
        return prefixes[hash % prefixes.length] + (hash % 999);
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvSubtitle;

        ViewHolder(View itemView) {
            super(itemView);
            // Ensure these IDs exist in your item_conversation.xml
            tvTitle = itemView.findViewById(R.id.tvChatName);
            tvSubtitle = itemView.findViewById(R.id.tvLastMessage);
        }
    }
}