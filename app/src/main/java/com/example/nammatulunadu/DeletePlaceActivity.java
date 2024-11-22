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

public class DeletePlaceActivity extends AppCompatActivity {
    Toolbar tb;
    EditText placeNameEditText;
    Button deletePlaceButton;
    private PlaceService placeService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        placeService = ApiClient.getRetrofitInstance().create(PlaceService.class);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_delete_place);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        tb=findViewById(R.id.tb);
        setSupportActionBar(tb);
        placeNameEditText = findViewById(R.id.placeNameDeleteText);
        deletePlaceButton = findViewById(R.id.deletePlaceButton);

        deletePlaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String placeName = placeNameEditText.getText().toString();
                if(placeName.isEmpty())
                {
                    Toast.makeText(DeletePlaceActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    deletePlace(placeName);
                }
            }
        });
    }
    private void deletePlace(String name) {
        Call<Responsemsg> call = placeService.deletePlace(name);
        call.enqueue(new Callback<Responsemsg>() {
            @Override
            public void onResponse(Call<Responsemsg> call, Response<Responsemsg> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getMessage().equals("Place Deleted")) {
                        Log.d("DeletePlaceActivity", "Place successfully deleted.");
                        Toast.makeText(DeletePlaceActivity.this, "Place deleted successfully", Toast.LENGTH_SHORT).show();
                        finish();
                        // Handle success, e.g., update UI
                    } else {
                        Log.e("DeletePlaceActivity", "Unexpected response: " + response.body());
                        Toast.makeText(DeletePlaceActivity.this, "Facing Trouble!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("DeletePlaceActivity", "Failed to delete place: " + response.message());
                    Toast.makeText(DeletePlaceActivity.this, "Place not found/Failed to delete", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Responsemsg> call, Throwable t) {
                Log.e("DeletePlaceActivity", "Error deleting place", t);
                Toast.makeText(DeletePlaceActivity.this, "Facing Trouble!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}