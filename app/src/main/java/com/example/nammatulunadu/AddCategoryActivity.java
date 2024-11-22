package com.example.nammatulunadu;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCategoryActivity extends AppCompatActivity {
    Toolbar tb;
    EditText categoryNameEditText;
    Button saveCategoryButton;
    private PlaceService placeService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        placeService = ApiClient.getRetrofitInstance().create(PlaceService.class);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_category);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        tb = findViewById(R.id.tb);
        setSupportActionBar(tb);
        categoryNameEditText = findViewById(R.id.categoryNameEditText);
        saveCategoryButton = findViewById(R.id.saveCategoryButton);

        saveCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String categoryName = categoryNameEditText.getText().toString();
                if (categoryName.isEmpty()) {
                    Toast.makeText(AddCategoryActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    addCategory(categoryName);
                }
            }
        });

    }
    private void addCategory(String name) {
        Call<Responsemsg> call = placeService.addCategory(name);
        call.enqueue(new Callback<Responsemsg>() {
            @Override
            public void onResponse(Call<Responsemsg> call, Response<Responsemsg> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getMessage().equals("Category Added")) {
                        Log.d("AddCategoryActivity", "Category successfully added.");
                        Toast.makeText(AddCategoryActivity.this, "Category added successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Log.e("AddCategoryActivity", "Unexpected response: " + response.body());
                        Toast.makeText(AddCategoryActivity.this, "Facing Trouble!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("AddCategoryActivity", "Failed to add Category: " + response.message());
                    Toast.makeText(AddCategoryActivity.this, "Failed to add Category", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Responsemsg> call, Throwable t) {
                Log.e("AddCategoryActivity", "Error adding Category", t);
                Toast.makeText(AddCategoryActivity.this, "Facing Trouble!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}