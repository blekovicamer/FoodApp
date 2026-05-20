package com.example.pitica;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private List<Order> orderList;

    public OrderAdapter(List<Order> orderList) { this.orderList = orderList; }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.tvOrderDetails.setText(order.getFoodName() + " ordered by " + order.getBuyerName());
        holder.tvStatus.setText("Status: " + order.getStatus());
    }

    @Override
    public int getItemCount() { return orderList.size(); }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderDetails, tvStatus;
        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ensure you have these IDs in item_order.xml
            tvOrderDetails = itemView.findViewById(R.id.tvOrderDetails);
            tvStatus = itemView.findViewById(R.id.tvStatus);
        }
    }
}