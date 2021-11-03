package com.example.ht;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button login;
        Button create;


        login = findViewById(R.id.startLogin_button);
        create = findViewById(R.id.startNewAccount_button);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToCreate();

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToLogin();
            }
        });
    }

    // starts the Create new user activity
    private void goToCreate(){
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }

    //starts the login activity
    private void goToLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}