package com.example.pitica;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ChefProfileActivity extends AppCompatActivity {
    private RecyclerView rvChefItems;
    private FoodAdapter foodAdapter;
    private List<FoodItem> chefFoodList;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_profile);

        // Get the chef name passed from the previous activity
        String chefName = getIntent().getStringExtra("chef_name");
        TextView tvName = findViewById(R.id.tvChefProfileName);
        if (chefName != null) {
            tvName.setText(chefName);
        }

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Setup RecyclerView
        rvChefItems = findViewById(R.id.rvChefItems);
        rvChefItems.setLayoutManager(new LinearLayoutManager(this));

        chefFoodList = new ArrayList<>();
        foodAdapter = new FoodAdapter(chefFoodList, item -> {
            // Handle food click if needed
        });
        rvChefItems.setAdapter(foodAdapter);

        // Fetch this chef's specific items
        if (chefName != null) {
            loadChefFood(chefName);
        }
    }

    private void loadChefFood(String chefName) {
        db.collection("food")
                .whereEqualTo("chefName", chefName) // Filters to only show items by this chef
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    chefFoodList.clear();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        FoodItem item = document.toObject(FoodItem.class);
                        chefFoodList.add(item);
                    }
                    foodAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error loading chef's food", Toast.LENGTH_SHORT).show();
                });
    }
}