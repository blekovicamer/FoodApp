package com.example.pitica;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        RecyclerView rvCart = findViewById(R.id.rvCart);
        rvCart.setLayoutManager(new LinearLayoutManager(this));

        // Get the list
        List<FoodItem> myCart = CartManager.getInstance().getCartItems();

        // Setup Adapter
        FoodAdapter adapter = new FoodAdapter(myCart, item -> {});
        rvCart.setAdapter(adapter);

        // Find the new Checkout button
        Button btnCheckout = findViewById(R.id.btnCheckout);
        // Inside btnCheckout.setOnClickListener...
        btnCheckout.setOnClickListener(v -> {
            if (myCart.isEmpty()) {
                Toast.makeText(this, "Cart empty!", Toast.LENGTH_SHORT).show();
            } else {
                for (FoodItem item : myCart) {
                    // Add to My History (User bought it)
                    OrderManager.getUserHistory().add(new Order(item.getTitle(), "My Purchase", "Delivered"));

                    // Add to Sales Received (Someone bought it)
                    OrderManager.getChefReceived().add(new Order(item.getTitle(), "Customer", "Pending"));
                }
                myCart.clear();
                adapter.notifyDataSetChanged();
                Toast.makeText(this, "Checkout Successful!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}