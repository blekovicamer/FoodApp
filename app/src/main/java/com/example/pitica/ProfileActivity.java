package com.example.pitica;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView; // 1. Don't forget this import
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore; // 2. Import Firestore

public class ProfileActivity extends AppCompatActivity {

    // 3. Define these globally so they are accessible everywhere in the class
    private FirebaseFirestore db;
    private TextView tvWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // 4. Initialize Firestore and the TextView
        db = FirebaseFirestore.getInstance();
        tvWelcome = findViewById(R.id.tvWelcome); // Ensure this ID matches your XML!

        // Define Buttons
        Button btnPostFood = findViewById(R.id.btnPostFood);
        Button btnOrderHistory = findViewById(R.id.btnOrderHistory);
        Button btnMyOrders = findViewById(R.id.btnMyOrders);
        Button btnLogout = findViewById(R.id.btnLogout);

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

        // Logout Logic
        btnLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        // Fetch User Data
        String currentUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        db.collection("users").document(currentUid)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String nickname = documentSnapshot.getString("nickname");
                        // Set the text dynamically
                        tvWelcome.setText("Welcome back, " + nickname);
                    }
                });
    }
}