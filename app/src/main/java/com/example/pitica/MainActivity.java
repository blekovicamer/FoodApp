package com.example.pitica;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // 1. Declare variables (Fixed: public static allows access from other activities)
    private RecyclerView rvFoodItems;
    private FoodAdapter foodAdapter;
    public static List<FoodItem> foodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 2. Setup RecyclerView
        rvFoodItems = findViewById(R.id.rvFoodItems);
        rvFoodItems.setLayoutManager(new LinearLayoutManager(this));

        // 3. Load Data & Attach Adapter
        loadTestData();
        foodAdapter = new FoodAdapter(foodList, item -> {
            Intent intent = new Intent(MainActivity.this, FoodDetailActivity.class);
            intent.putExtra("title", item.getTitle());
            intent.putExtra("chef", item.getChefName());
            intent.putExtra("price", item.getPrice());
            startActivity(intent);
        });
        rvFoodItems.setAdapter(foodAdapter);

        // 4. Navigation Click Listeners
        Button btnNavHome = findViewById(R.id.btnNavHome);
        Button btnNavCart = findViewById(R.id.btnNavCart);
        Button btnNavProfile = findViewById(R.id.btnNavProfile);
        Button btnNavChat = findViewById(R.id.btnNavChat);

        btnNavCart.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, CartActivity.class));
        });

        btnNavProfile.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ProfileActivity.class));
        });

        btnNavChat.setOnClickListener(v -> {
            startActivity(new Intent(this, ConversationsActivity.class));
        });
    }

    private void loadTestData() {
        foodList = new ArrayList<>();
        // Just keep the hardcoded stuff here
        foodList.add(new FoodItem("Burek", "Lejla", "12.00 KM", "2.5 km"));
        foodList.add(new FoodItem("Sirnica", "Lejla", "11.00 KM", "2.5 km"));
        foodList.add(new FoodItem("Sarma", "Amira", "15.00 KM", "3.0 km"));
        foodList.add(new FoodItem("Corba", "Amira", "8.00 KM", "3.0 km"));
        foodList.add(new FoodItem("Kompirusa", "Tarik", "10.00 KM", "1.2 km"));
        foodList.add(new FoodItem("Riza", "Tarik", "9.00 KM", "1.2 km"));
    }
}