package com.example.nammatulunadu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AdminpageActivity extends AppCompatActivity {
    Button addPlaceButton, addCategoryButton, deletePlaceButton, deleteCategoryButton;
    TextView name;
    Toolbar tb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_adminpage);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        name = findViewById(R.id.adminname);
        String adminname = getIntent().getStringExtra("name");
        name.setText(adminname);
        addPlaceButton = findViewById(R.id.addplacebtn);
        addCategoryButton = findViewById(R.id.addcatbtn);
        deletePlaceButton = findViewById(R.id.delplacebtn);
        deleteCategoryButton = findViewById(R.id.delcatbtn);

        addPlaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminpageActivity.this, AddPlaceActivity.class));
            }
        });
        addCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminpageActivity.this, AddCategoryActivity.class));
            }
        });
        deletePlaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminpageActivity.this, DeletePlaceActivity.class));
            }
        });
        deleteCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminpageActivity.this, DeleteCategoryActivity.class));
            }
        });
    }
}