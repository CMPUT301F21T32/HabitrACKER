package com.example.ht;

/**
 * START ACTIVITY
 * @author cole
 *
 * This is a control class that creates a screen
 * that will be initially seen when users first open the app
 * They can then choose to either create an account or
 * log in to an existing one
 */

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
        finish();
    }

    //starts the login activity
    private void goToLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}