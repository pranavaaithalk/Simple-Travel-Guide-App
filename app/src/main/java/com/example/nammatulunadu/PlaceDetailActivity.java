package com.example.nammatulunadu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceDetailActivity extends AppCompatActivity {

    TextView placeNameTextView, categoryNameTextView, descriptionTextView;
    List<Place> place;
    Toolbar tb;
    private PlaceService placeService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        placeService = ApiClient.getRetrofitInstance().create(PlaceService.class);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_place_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        tb = findViewById(R.id.tb);
        setSupportActionBar(tb);
        placeNameTextView = findViewById(R.id.placeNameTextView);
        categoryNameTextView = findViewById(R.id.categoryNameTextView1);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        String pname = getIntent().getStringExtra("name");
        fetchDetails(pname);


    }
    private void fetchDetails(String name) {
        Call<ApiResponce> call = placeService.getPlacep(name);
        call.enqueue(new Callback<ApiResponce>() {
            @Override
            public void onResponse(Call<ApiResponce> call, Response<ApiResponce> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponce apiResponse = response.body();
                    if ("success".equals(apiResponse.getStatus())) {

                        String name = apiResponse.getData().getName();
                        String description = apiResponse.getData().getDescription();
                        String category = apiResponse.getData().getCategory();
                        placeNameTextView.setText(name);
                        descriptionTextView.setText(description);
                        categoryNameTextView.setText(category);
                    } else {
                        Toast.makeText(PlaceDetailActivity.this, "Place not found", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } else {
                    Toast.makeText(PlaceDetailActivity.this, "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ApiResponce> call, Throwable t) {
                Toast.makeText(PlaceDetailActivity.this, "Failed to connect to server", Toast.LENGTH_SHORT).show();
                Log.e("PlaceDetailActivity", "Error: ", t);
                finish();
            }
        });
    }

}