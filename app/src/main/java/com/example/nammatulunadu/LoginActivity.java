package com.example.nammatulunadu;

import android.content.Intent;
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

public class LoginActivity extends AppCompatActivity {

    EditText usernameEditText, passcode, passwordEditText;
    Button loginButton, signupButton;
    Toolbar tb;
    static int code=73;
    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userService = ApiClient.getRetrofitInstance().create(UserService.class);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        tb = findViewById(R.id.tb);
        setSupportActionBar(tb);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        signupButton = findViewById(R.id.signupButton);
        passcode = findViewById(R.id.passcodeEditText);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }else {
                    login(username, password);
                    usernameEditText.setText("");
                    passwordEditText.setText("");
                    finish();
                }
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String pcode = passcode.getText().toString();
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            if(username.isEmpty() || password.isEmpty() || pcode.isEmpty()){
                Toast.makeText(LoginActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            }else{
            int pc = Integer.parseInt(pcode);
            if(pc==code){
                signup(username, password);
                usernameEditText.setText("");
                passwordEditText.setText("");
                passcode.setText("");
            } else {
            Toast.makeText(LoginActivity.this, "Invalid Passcode", Toast.LENGTH_SHORT).show();
            passcode.setText("");
            passcode.requestFocus();
            }}
        }
        });
    }
    private void signup(String username, String password) {
        Call<Responsemsg> call = userService.signup(username, password);
        call.enqueue(new Callback<Responsemsg>() {
            @Override
            public void onResponse(Call<Responsemsg> call, Response<Responsemsg> response) {
                if (response.body() != null && response.body().getMessage().equals("Signup Successful")) {
                    Toast.makeText(LoginActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Responsemsg> call, Throwable t) {
                Log.e("LoginActivity", "Error during signup", t);
                Toast.makeText(LoginActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void login(String username, String password) {
        Call<Responsemsg> call = userService.login(username, password);
        call.enqueue(new Callback<Responsemsg>() {
            @Override
            public void onResponse(Call<Responsemsg> call, Response<Responsemsg> response) {
                if (response.body() != null && response.body().getMessage().equals("Login Successful")) {
                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, AdminpageActivity.class);
                    intent.putExtra("name", username);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "User Not Found!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Responsemsg> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}