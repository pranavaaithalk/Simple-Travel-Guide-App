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

public class DeleteCategoryActivity extends AppCompatActivity {
    Toolbar tb;
    EditText categoryNameDeleteText;
    Button deleteCategoryButton;
    private PlaceService placeService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        placeService = ApiClient.getRetrofitInstance().create(PlaceService.class);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_delete_category);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        tb=findViewById(R.id.tb);
        setSupportActionBar(tb);
        categoryNameDeleteText = findViewById(R.id.categoryNameDeleteText);
        deleteCategoryButton = findViewById(R.id.deleteCategoryButton);

        deleteCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String placeName = categoryNameDeleteText.getText().toString();
                if(placeName.isEmpty())
                {
                    Toast.makeText(DeleteCategoryActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    deleteCategory(placeName);
                }
            }
        });
    }
    private void deleteCategory(String name) {
        Call<Responsemsg> call = placeService.deleteCategory(name);
        call.enqueue(new Callback<Responsemsg>() {
            @Override
            public void onResponse(Call<Responsemsg> call, Response<Responsemsg> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getMessage().equals("Category Deleted")) {
                        Log.d("DeleteCategoryActivity", "Category successfully deleted.");
                        Toast.makeText(DeleteCategoryActivity.this, "Category deleted successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Log.e("DeleteCategoryActivity", "Unexpected response: " + response.body().getMessage());
                        Toast.makeText(DeleteCategoryActivity.this, "Facing Trouble!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("DeleteCategoryActivity", "Failed to delete Category: " + response.message());
                    Toast.makeText(DeleteCategoryActivity.this, "Category not found/Failed to delete", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Responsemsg> call, Throwable t) {
                Log.e("DeleteCategoryActivity", "Error deleting Category", t);
                Toast.makeText(DeleteCategoryActivity.this, "Facing Trouble!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}