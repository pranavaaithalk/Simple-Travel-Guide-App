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

public class AddPlaceActivity extends AppCompatActivity {
    Toolbar tb;
    EditText placeNameEditText,categoryNameEditText, placeDescriptionEditText;
    Button savePlaceButton;
    boolean f=false;
    private PlaceService placeService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        placeService = ApiClient.getRetrofitInstance().create(PlaceService.class);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_place);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        tb = findViewById(R.id.tb);
        setSupportActionBar(tb);
        placeNameEditText = findViewById(R.id.placeNameEditText);
        categoryNameEditText = findViewById(R.id.categoryNameEditText);
        placeDescriptionEditText = findViewById(R.id.placeDescriptionEditText);
        savePlaceButton = findViewById(R.id.savePlaceButton);

        savePlaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String placeName = placeNameEditText.getText().toString();
                String categoryName = categoryNameEditText.getText().toString();
                String description = placeDescriptionEditText.getText().toString();
                if (placeName.isEmpty() || categoryName.isEmpty() || description.isEmpty()) {
                    Toast.makeText(AddPlaceActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    checkCat(categoryName,placeName,description);
                }
            }
        });
    }
    private void addPlace(String name, String category, String description) {
        Call<Responsemsg> call = placeService.addPlace(name, category, description);
        call.enqueue(new Callback<Responsemsg>() {
            @Override
            public void onResponse(Call<Responsemsg> call, Response<Responsemsg> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getMessage().equals("Place Added")) {
                        Log.d("AddPlaceActivity", "Place successfully added.");
                        Toast.makeText(AddPlaceActivity.this, "Place added successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Log.e("AddPlaceActivity", "Unexpected response: " + response.body());
                        Toast.makeText(AddPlaceActivity.this, "Facing Trouble!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("AddPlaceActivity_addPlace", "Failed to add place: " + response.message());
                    Toast.makeText(AddPlaceActivity.this, "Failed to add place", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Responsemsg> call, Throwable t) {
                Log.e("AddPlaceActivity", "Error adding place", t);
            }
        });
    }

    private void checkCat(String name, String placeName, String description) {
        Call<Responsemsg> call = placeService.checkCategory(name);
        call.enqueue(new Callback<Responsemsg>() {
            @Override
            public void onResponse(Call<Responsemsg> call, Response<Responsemsg> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getMessage().equals("Category exists")) {
                        Log.d("AddPlaceActivity", "Category exists.");
                        addPlace(placeName, name, description);
                    } else {
                        Log.e("AddPlaceActivity", "Category Doesn't exist!");
                        Toast.makeText(AddPlaceActivity.this, "Category Doesn't exist!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("AddPlaceActivity_checkCat", "Error: " + response.message());
                    Toast.makeText(AddPlaceActivity.this, "Category Doesn't exist!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Responsemsg> call, Throwable t) {
                Log.e("AddPlaceActivity", "Error checking category", t);
                Toast.makeText(AddPlaceActivity.this, "Error checking category!", Toast.LENGTH_SHORT).show();
            }
        });
    }


}