package com.example.nammatulunadu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

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

public class PlaceListActivity extends AppCompatActivity {
    Toolbar tb;
    LinearLayout placelistlayout;
    private PlaceService placeService;
    List<Place> places;
    String cname;

    @Override
    protected void onResume() {
        super.onResume();
        if(cname.equals("all"))
        {
            fetchAllPlaces();
        }else {
            fetchPlaces(cname);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_place_list);
        placeService = ApiClient.getRetrofitInstance().create(PlaceService.class);
        cname = getIntent().getStringExtra("name");
        if(cname.equals("all"))
        {
            fetchAllPlaces();
        }else {
            fetchPlaces(cname);
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        tb=findViewById(R.id.tb);
        setSupportActionBar(tb);
    }
    private void fetchPlaces(String cname){
        Call<List<Place>> call = placeService.getCategoriesplace(cname);
        call.enqueue(new Callback<List<Place>>() {
            @Override
            public void onResponse(Call<List<Place>> call, Response<List<Place>> response) {
                placelistlayout=findViewById(R.id.placelistlayout);
                placelistlayout.removeAllViews();
                if (response.isSuccessful() && response.body() != null) {
                    places = response.body();
                    if(!places.isEmpty()) {
                        for (Place place : places) {
                            addPlaceCard(place,placelistlayout);
                        }
                    }else{
                        Place p = new Place("No Places Found", "", "");
                        addPlaceCard(p,placelistlayout);
                    }
                } else {
                    Log.e("MainActivity", "Request failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Place>> call, Throwable t) {
                Log.e("MainActivity", "Network request failed", t);
            }
        });
    }

    private void fetchAllPlaces(){
        Call<List<Place>> call = placeService.getPlaces();
        call.enqueue(new Callback<List<Place>>() {
            @Override
            public void onResponse(Call<List<Place>> call, Response<List<Place>> response) {
                placelistlayout=findViewById(R.id.placelistlayout);
                placelistlayout.removeAllViews();
                if (response.isSuccessful() && response.body() != null) {
                    places = response.body();
                    if(!places.isEmpty()) {
                        for (Place place : places) {
                            addPlaceCard(place,placelistlayout);
                        }
                    }else{
                        Place p = new Place("No Places Found", "", "");
                        addPlaceCard(p,placelistlayout);
                    }
                } else {
                    Log.e("MainActivity", "Request failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Place>> call, Throwable t) {
                Log.e("MainActivity", "Network request failed", t);
            }
        });
    }
    private void addPlaceCard(Place place, LinearLayout layout) {
        // Inflate card layout
        LayoutInflater inflater = LayoutInflater.from(this);
        View cardViewp = inflater.inflate(R.layout.category_place_item, layout, false);

        // Get references to card elements
        TextView title = cardViewp.findViewById(R.id.Titlecard);
        TextView cat = cardViewp.findViewById(R.id.Catcard);
        // Set card title and description
        title.setText(place.getName());
        cat.setText(place.getCategory());
        cardViewp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PlaceListActivity.this,PlaceDetailActivity.class);
                i.putExtra("name",place.getName());
                startActivity(i);
            }
        });

        placelistlayout.addView(cardViewp);
    }
}