package com.example.pitica;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<User> userList;

    public UserAdapter(List<User> userList) { this.userList = userList; }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.tvNickname.setText(user.getNickname());

        holder.itemView.setOnClickListener(v -> {
            // When clicked, start ChatActivity and pass the friend's info
            Intent intent = new Intent(v.getContext(), ChatActivity.class);
            intent.putExtra("friendUid", user.getUid());
            intent.putExtra("friendNickname", user.getNickname());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() { return userList.size(); }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView tvNickname;
        UserViewHolder(View v) {
            super(v);
            tvNickname = v.findViewById(android.R.id.text1);
        }
    }
}