package com.example.pitica;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Define Buttons
        Button btnPostFood = findViewById(R.id.btnPostFood);
        Button btnOrderHistory = findViewById(R.id.btnOrderHistory);
        Button btnMyOrders = findViewById(R.id.btnMyOrders);

        // Navigate to Post Food Activity
        btnPostFood.setOnClickListener(v -> {
            startActivity(new Intent(this, PostFoodActivity.class));
        });

        // Navigate to Order History
        btnOrderHistory.setOnClickListener(v -> {
            startActivity(new Intent(this, OrderHistoryActivity.class));
        });

        // Navigate to Seller Orders
        btnMyOrders.setOnClickListener(v -> {
            startActivity(new Intent(this, SellerOrdersActivity.class));
        });
    }
}