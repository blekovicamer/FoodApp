package com.example.pitica;

import android.os.Bundle;
import android.widget.Button; // 1. Import Button
import android.widget.TextView;
import android.widget.Toast; // 2. Import Toast
import androidx.appcompat.app.AppCompatActivity;

public class FoodDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        // ... (Existing code to get data and set text) ...
        String title = getIntent().getStringExtra("title");
        String chef = getIntent().getStringExtra("chef");
        String price = getIntent().getStringExtra("price");

        TextView tvTitle = findViewById(R.id.tvDetailTitle);
        TextView tvChef = findViewById(R.id.tvDetailChef);
        TextView tvPrice = findViewById(R.id.tvDetailPrice);

        tvTitle.setText(title);
        tvChef.setText("Prepared by: " + chef);
        tvPrice.setText(price);

        // 3. Add the click listener to the Order button
        Button btnOrder = findViewById(R.id.btnOrder);
        btnOrder.setOnClickListener(v -> {
            // This shows the "Added to Cart" message
            Toast.makeText(FoodDetailActivity.this, "Added to Cart: " + title, Toast.LENGTH_SHORT).show();

            // Optional: Close the screen after adding to cart
            // finish();
        });
    }
}