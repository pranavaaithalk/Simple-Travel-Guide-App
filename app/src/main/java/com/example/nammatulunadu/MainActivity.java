package com.example.nammatulunadu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity{
    Toolbar tb;
    List<Place> places;
    List<Category> categories;
    private ApiService apiService;

    @Override
    protected void onResume() {
        super.onResume();
        checks();
        fetchCategories();
        fetchPlaces();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.menubutton){
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = new MenuInflater(this);
        mi.inflate(R.menu.optmenumain,menu);
        return super.onCreateOptionsMenu(menu);
    }

    EditText searchtxt;
    ImageButton findButton;
    LinearLayout categorylistlayout, placelistlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        checks();
        fetchPlaces();
        fetchCategories();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        tb=findViewById(R.id.tb);
        searchtxt=findViewById(R.id.searchtxt);
        findButton=findViewById(R.id.findButton);
        setSupportActionBar(tb);

        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,PlaceDetailActivity.class);
                i.putExtra("name",searchtxt.getText().toString());
                startActivity(i);
                searchtxt.setText("");
            }
        });


    }

    private void fetchPlaces() {
        Call<List<Place>> call = apiService.getPlaces();

        call.enqueue(new Callback<List<Place>>() {
            @Override
            public void onResponse(Call<List<Place>> call, Response<List<Place>> response) {
                placelistlayout=findViewById(R.id.placelistlayout);
                placelistlayout.removeAllViews();
                if (response.isSuccessful() && response.body() != null) {
                    places = response.body();
                    if(!places.isEmpty()) {
                        if(places.size()<=4) {
                            for (int i = 0; i < 4; i++) {
                                addPlaceCard(places.get(i), placelistlayout,1);
                            }

                        }else{
                        for (int i = 0; i < 3; i++) {
                            addPlaceCard(places.get(i),placelistlayout,1);
                        }
                            Place p = new Place("More...", "", "");
                            addPlaceCard(p,placelistlayout,2);
                        }
                    }else{
                        Place p = new Place("No Places Found", "", "");
                        addPlaceCard(p,placelistlayout,0);
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
    private void fetchCategories() {
        Call<List<Category>> call = apiService.getCategories();

        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                categorylistlayout=findViewById(R.id.categorylistlayout);
                categorylistlayout.removeAllViews();
                if (response.isSuccessful() && response.body() != null) {
                    categories = response.body();
                    if(!categories.isEmpty()){
                        for (Category category : categories) {
                            addCategoryCard(category,categorylistlayout,1);
                        }
                    }else{
                        Category c = new Category("No Categories Found");
                        addCategoryCard(c,categorylistlayout,0);
                    }
                } else {
                    Log.e("MainActivity", "Request failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.e("MainActivity", "Network request failed", t);
            }
        });
    }
    private void addPlaceCard(Place place, LinearLayout layout, int flag) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View cardViewp = inflater.inflate(R.layout.category_place_item, layout, false);

        TextView title = cardViewp.findViewById(R.id.Titlecard);
        TextView cat = cardViewp.findViewById(R.id.Catcard);
        title.setText(place.getName());
        cat.setText(place.getCategory());
        if(flag==1) {
            cardViewp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(MainActivity.this, PlaceDetailActivity.class);
                    i.putExtra("name", place.getName());
                    startActivity(i);
                }
            });
        }
        else if(flag==2){
            cardViewp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(MainActivity.this, PlaceListActivity.class);
                    i.putExtra("name", "all");
                    startActivity(i);
                }
        });
        }

        placelistlayout.addView(cardViewp);
    }
    private void addCategoryCard(Category category, LinearLayout layout,int flag) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View cardViewc = inflater.inflate(R.layout.card_place_item, layout, false);

        TextView title = cardViewc.findViewById(R.id.cardTitle);
        title.setText(category.getName());
        cardViewc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag==1) {
                    Intent i = new Intent(MainActivity.this, PlaceListActivity.class);
                    i.putExtra("name", category.getName());
                    startActivity(i);
                }
            }
        });

        categorylistlayout.addView(cardViewc);
    }
    private void checks()
    {
        Call<Responsemsg> call = apiService.check_server();
        call.enqueue(new Callback<Responsemsg>() {
        @Override
        public void onResponse(Call<Responsemsg> call, Response<Responsemsg> response) {
            if (response.isSuccessful() && response.body() != null) {
                if(response.body().getMessage().equals("Server Connected"))
                    Log.d("Server_status", "Server Message: " + response.body().getMessage());
            }
        }

        @Override
        public void onFailure(Call<Responsemsg> call, Throwable t) {
            AlertDialog.Builder ab = new AlertDialog.Builder(MainActivity.this);
            ab.setTitle("Alert: Server Offline!");
            ab.setMessage("Try again later.");
            ab.setPositiveButton("OK",null);
            AlertDialog ad = ab.create();
            ad.show();
        }
    });
    }
}