package com.example.pitica;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Views
    private RecyclerView rvHorizontal, rvFoodItems;
    private FoodAdapter horizontalAdapter, mainAdapter;
    private List<FoodItem> horizontalList, mainList;

    // Firebase
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();

        setupRecyclerViews();
        setupNavigation();

        // Load data from Firebase
        fetchRecentFood();
        fetchAllFood();
    }

    private void setupRecyclerViews() {
        // 1. Horizontal "Newly Added" List
        rvHorizontal = findViewById(R.id.rvHorizontal);
        rvHorizontal.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        horizontalList = new ArrayList<>();
        horizontalAdapter = new FoodAdapter(horizontalList, this::openDetail);
        rvHorizontal.setAdapter(horizontalAdapter);

        // 2. Vertical "All Food" List
        rvFoodItems = findViewById(R.id.rvFoodItems);
        rvFoodItems.setLayoutManager(new LinearLayoutManager(this));
        rvFoodItems.setNestedScrollingEnabled(false); // Required for NestedScrollView
        mainList = new ArrayList<>();
        mainAdapter = new FoodAdapter(mainList, this::openDetail);
        rvFoodItems.setAdapter(mainAdapter);
    }

    private void fetchRecentFood() {
        db.collection("food")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .limit(5)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    horizontalList.clear();
                    horizontalList.addAll(queryDocumentSnapshots.toObjects(FoodItem.class));
                    horizontalAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to load recent", Toast.LENGTH_SHORT).show());
    }

    private void fetchAllFood() {
        db.collection("food")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    mainList.clear();
                    mainList.addAll(queryDocumentSnapshots.toObjects(FoodItem.class));
                    mainAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to load feed", Toast.LENGTH_SHORT).show());
    }

    private void openDetail(FoodItem item) {
        Intent intent = new Intent(MainActivity.this, FoodDetailActivity.class);
        intent.putExtra("title", item.getTitle());
        intent.putExtra("chef", item.getChefName());
        intent.putExtra("price", item.getPrice());
        startActivity(intent);
    }

    private void setupNavigation() {
        ImageButton btnNavHome = findViewById(R.id.btnNavHome);
        ImageButton btnNavCart = findViewById(R.id.btnNavCart);
        ImageButton btnNavProfile = findViewById(R.id.btnNavProfile);
        ImageButton btnNavChat = findViewById(R.id.btnNavChat);

        btnNavCart.setOnClickListener(v -> startActivity(new Intent(this, CartActivity.class)));
        btnNavProfile.setOnClickListener(v -> startActivity(new Intent(this, ProfileActivity.class)));
        btnNavChat.setOnClickListener(v -> startActivity(new Intent(this, ConversationsActivity.class)));
    }
}