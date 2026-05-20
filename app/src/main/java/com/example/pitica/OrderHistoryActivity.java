package com.example.pitica;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class OrderHistoryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_orders); // Reuse the same layout as seller orders

        RecyclerView rvHistory = findViewById(R.id.rvOrders);
        rvHistory.setLayoutManager(new LinearLayoutManager(this));

        // Point to the History list specifically
        rvHistory.setAdapter(new OrderAdapter(OrderManager.getUserHistory()));
    }
}