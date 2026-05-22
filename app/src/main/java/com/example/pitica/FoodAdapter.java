package com.example.pitica;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView; // Added
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.io.File;
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

        holder.tvFoodName.setText(item.getTitle());
        holder.tvChefInfo.setText("By " + item.getChefName() + " • " + item.getDistance());
        holder.tvPrice.setText(item.getPrice());

        // LOAD LOCAL IMAGE
        String path = item.getImagePath();
        if (path != null && !path.isEmpty()) {
            File imgFile = new File(path);
            if (imgFile.exists()) {
                holder.ivFoodImage.setImageURI(Uri.fromFile(imgFile));
            }
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(item);
        });

        holder.btnAdd.setOnClickListener(v -> {
            CartManager.getInstance().addItem(item);
            Toast.makeText(v.getContext(), item.getTitle() + " added to cart!", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() { return foodList.size(); }

    public static class FoodViewHolder extends RecyclerView.ViewHolder {
        TextView tvFoodName, tvChefInfo, tvPrice;
        Button btnAdd;
        ImageView ivFoodImage;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFoodName = itemView.findViewById(R.id.tvFoodName);
            tvChefInfo = itemView.findViewById(R.id.tvChefInfo);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            btnAdd = itemView.findViewById(R.id.btnAdd);

            // --- FIX THIS LINE HERE ---
            // Change ivFoodImage to match your XML ID
            ivFoodImage = itemView.findViewById(R.id.imgFood);
        }
    }
}