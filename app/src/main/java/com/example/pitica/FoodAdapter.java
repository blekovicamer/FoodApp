package com.example.pitica;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {
    private List<FoodItem> foodList;
    private OnItemClickListener listener;

    public interface OnItemClickListener { void onItemClick(FoodItem item); }

    public FoodAdapter(List<FoodItem> foodList, OnItemClickListener listener) {
        this.foodList = foodList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        FoodItem item = foodList.get(position);
        holder.tvTitle.setText(item.getTitle());
        holder.tvChefDistance.setText("By " + item.getChefName() + " • " + item.getDistance());
        holder.tvPrice.setText(item.getPrice());

        holder.itemView.setOnClickListener(v -> { if (listener != null) listener.onItemClick(item); });

        holder.btnAddToCart.setOnClickListener(v -> {
            CartManager.getInstance().addItem(item);
            Toast.makeText(v.getContext(), item.getTitle() + " added to cart!", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() { return foodList.size(); }

    public static class FoodViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvChefDistance, tvPrice;
        Button btnAddToCart;
        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvFoodTitle);
            tvChefDistance = itemView.findViewById(R.id.tvChefDistance);
            tvPrice = itemView.findViewById(R.id.tvFoodPrice);
            btnAddToCart = itemView.findViewById(R.id.btnAddToCart);
        }
    }
}