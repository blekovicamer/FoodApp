package com.example.pitica;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ConversationsAdapter extends RecyclerView.Adapter<ConversationsAdapter.ViewHolder> {

    private List<Chat> chatList;

    public ConversationsAdapter(List<Chat> chatList) {
        this.chatList = chatList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // You can reuse android.R.layout.simple_list_item_2 for a quick title/subtitle view
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Chat chat = chatList.get(position);

        holder.tvTitle.setText("Chat"); // You can fetch the friend's name here later
        holder.tvSubtitle.setText(chat.getLastMessage());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), ChatActivity.class);
            // Pass the chatId so the ChatActivity knows which room to open
            intent.putExtra("chatId", chat.getChatId());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvSubtitle;
        ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(android.R.id.text1);
            tvSubtitle = itemView.findViewById(android.R.id.text2);
        }
    }
}