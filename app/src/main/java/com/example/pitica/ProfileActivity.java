package com.example.pitica;


import android.content.Intent;

import android.os.Bundle;

import android.widget.Button;

import android.widget.ImageButton;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.firestore.FirebaseFirestore;


public class ProfileActivity extends AppCompatActivity {


    private FirebaseFirestore db;

    private TextView tvWelcome;


    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);


// --- 1. SETUP FIREBASE AND VIEWS ---

        db = FirebaseFirestore.getInstance();

        tvWelcome = findViewById(R.id.tvWelcome);


// --- 2. SETUP PROFILE BUTTONS ---

        Button btnPostFood = findViewById(R.id.btnPostFood);

        Button btnOrderHistory = findViewById(R.id.btnOrderHistory);

        Button btnMyOrders = findViewById(R.id.btnMyOrders);

        Button btnLogout = findViewById(R.id.btnLogout);


        btnPostFood.setOnClickListener(v -> startActivity(new Intent(this, PostFoodActivity.class)));

        btnOrderHistory.setOnClickListener(v -> startActivity(new Intent(this, OrderHistoryActivity.class)));

        btnMyOrders.setOnClickListener(v -> startActivity(new Intent(this, SellerOrdersActivity.class)));


        btnLogout.setOnClickListener(v -> {

            FirebaseAuth.getInstance().signOut();

            Intent intent = new Intent(this, LoginActivity.class);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);

            finish();

        });


// --- 3. FETCH USER DATA ---

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {

            String currentUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

            db.collection("users").document(currentUid)

                    .get()

                    .addOnSuccessListener(documentSnapshot -> {

                        if (documentSnapshot.exists()) {

                            String nickname = documentSnapshot.getString("nickname");

                            tvWelcome.setText("Welcome back, " + nickname);

                        }

                    });

        }


// --- 4. NAVIGATION BAR LOGIC ---

// Ensure the IDs below match exactly the IDs in your layout_bottom_nav.xml

        ImageButton btnNavHome = findViewById(R.id.btnNavHome);

        ImageButton btnNavCart = findViewById(R.id.btnNavCart);

        ImageButton btnNavChat = findViewById(R.id.btnNavChat);

        ImageButton btnNavProfile = findViewById(R.id.btnNavProfile);


        btnNavHome.setOnClickListener(v -> {

            startActivity(new Intent(this, MainActivity.class));

            finish();

        });


        btnNavCart.setOnClickListener(v -> {

            startActivity(new Intent(this, CartActivity.class));

        });


        btnNavChat.setOnClickListener(v -> {

            startActivity(new Intent(this, ChatActivity.class));

        });


        btnNavProfile.setOnClickListener(v -> {

// Already on Profile page, do nothing

        });

    }

} 