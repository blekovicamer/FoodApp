package com.example.pitica;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

public class SearchUsersActivity extends AppCompatActivity {

    private EditText etSearch;
    private RecyclerView rvUserResults;
    private UserAdapter adapter; // We will create this next
    private List<User> userList;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_users);

        db = FirebaseFirestore.getInstance();
        etSearch = findViewById(R.id.etSearch);
        rvUserResults = findViewById(R.id.rvUserResults);
        Button btnSearch = findViewById(R.id.btnSearch);

        userList = new ArrayList<>();
        rvUserResults.setLayoutManager(new LinearLayoutManager(this));

        btnSearch.setOnClickListener(v -> performSearch(etSearch.getText().toString().trim()));
    }

    private void performSearch(String query) {
        db.collection("users")
                .whereEqualTo("nickname", query)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    userList.clear();
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        User user = doc.toObject(User.class);
                        userList.add(user);
                    }
                    adapter = new UserAdapter(userList);
                    rvUserResults.setAdapter(adapter);
                });
    }
}