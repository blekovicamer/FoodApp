package com.example.pitica;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PostFoodActivity extends AppCompatActivity {

    private ImageView ivFoodImage;
    private EditText etTitle, etPrice;
    private Button btnSubmit;
    private Uri imageUri;

    private final ActivityResultLauncher<String> pickImage = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    imageUri = uri;
                    ivFoodImage.setImageURI(uri);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_food);

        ivFoodImage = findViewById(R.id.ivFoodImage);
        etTitle = findViewById(R.id.etTitle);
        etPrice = findViewById(R.id.etPrice);
        btnSubmit = findViewById(R.id.btnSubmit);

        ivFoodImage.setOnClickListener(v -> pickImage.launch("image/*"));
        btnSubmit.setOnClickListener(v -> validateAndSave());
    }

    private void validateAndSave() {
        String title = etTitle.getText().toString().trim();
        String price = etPrice.getText().toString().trim();

        if (imageUri == null) {
            Toast.makeText(this, "Please select an image first", Toast.LENGTH_SHORT).show();
            return;
        }
        if (title.isEmpty() || price.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        btnSubmit.setEnabled(false);
        saveImageLocallyAndSaveData(title, price);
    }

    private void saveImageLocallyAndSaveData(String title, String price) {
        try {
            // 1. Create a unique filename
            String fileName = UUID.randomUUID().toString() + ".jpg";

            // 2. Open an input stream from the selected photo
            InputStream inputStream = getContentResolver().openInputStream(imageUri);

            // 3. Create a file in the app's internal storage
            File file = new File(getFilesDir(), fileName);
            FileOutputStream outputStream = new FileOutputStream(file);

            // 4. Copy the bytes
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            outputStream.close();
            inputStream.close();

            // 5. Save the local file path to Firestore
            String localPath = file.getAbsolutePath();
            saveToFirestore(title, price, localPath);

        } catch (Exception e) {
            Toast.makeText(this, "Error saving: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            btnSubmit.setEnabled(true);
        }
    }

    private void saveToFirestore(String title, String price, String localPath) {
        Map<String, Object> food = new HashMap<>();
        food.put("title", title);
        food.put("price", price + " KM");
        food.put("imagePath", localPath); // We store the path string
        food.put("timestamp", FieldValue.serverTimestamp());

        FirebaseFirestore.getInstance().collection("food").add(food)
                .addOnSuccessListener(doc -> {
                    Toast.makeText(this, "Saved locally!", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Database error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    btnSubmit.setEnabled(true);
                });
    }
}