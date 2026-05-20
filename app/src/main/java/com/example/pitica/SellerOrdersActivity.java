package com.example.pitica;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class SellerOrdersActivity extends AppCompatActivity {
    // You can add this list to your MainActivity to make it global
    public static List<Order> myOrders = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_orders);

        RecyclerView rvOrders = findViewById(R.id.rvOrders);
        rvOrders.setLayoutManager(new LinearLayoutManager(this));

        // Load data and set adapter
        // Inside SellerOrdersActivity.java
        rvOrders.setAdapter(new OrderAdapter(OrderManager.getChefReceived()));
    }
}