package com.example.pitica;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class PostFoodActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_food);

        EditText etTitle = findViewById(R.id.etTitle);
        EditText etPrice = findViewById(R.id.etPrice);
        Button btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(v -> {
            String title = etTitle.getText().toString();
            String price = etPrice.getText().toString();

            // Add to the global list!
            MainActivity.foodList.add(new FoodItem(title, "Amer B.", price + " KM", "0.0 km"));

            // Close the screen
            finish();
        });
    }
}