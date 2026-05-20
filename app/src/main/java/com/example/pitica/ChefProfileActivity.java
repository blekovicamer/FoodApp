package com.example.pitica;

import android.os.Bundle;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import java.util.List;
import java.util.ArrayList;

public class ChefProfileActivity extends AppCompatActivity {
    private RecyclerView rvChefItems;
    private FoodAdapter foodAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_profile);

        String chefName = getIntent().getStringExtra("chef_name");
        TextView tvName = findViewById(R.id.tvChefProfileName);
        tvName.setText(chefName);

        // Filter logic
        List<FoodItem> filteredList = new ArrayList<>();

        // Safety check: Ensure the list is loaded
        if (MainActivity.foodList != null) {
            for (FoodItem item : MainActivity.foodList) {
                // This now matches "Lejla" == "Lejla"
                if (item.getChefName().equals(chefName)) {
                    filteredList.add(item);
                }
            }
        }

        // Setup RecyclerView
        RecyclerView rv = findViewById(R.id.rvChefItems);
        rv.setLayoutManager(new LinearLayoutManager(this));

        // Pass the filtered list to your existing adapter
        rv.setAdapter(new FoodAdapter(filteredList, item -> {
            // Handle food click if needed
        }));
    }
}