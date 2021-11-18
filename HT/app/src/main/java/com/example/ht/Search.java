package com.example.ht;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class Search extends AppCompatActivity {
    String username;
    Button profileButton;
    Button homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        profileButton = findViewById(R.id.searchProfile_button);
        homeButton = findViewById(R.id.searchHome_button);

        Intent intent = getIntent();
        username = intent.getStringExtra("USERNAME");

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToProfile(username);
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToFeed(username);
            }
        });
    }



    //starts the profile activity
    private void goToProfile(String un){
        Intent intent = new Intent(this, SelfProfile.class);
        intent.putExtra("USERNAME", un);
        startActivity(intent);
        finish();
    }

    private void goToFeed(String un){
        Intent intent = new Intent(this, FeedActivity.class);
        intent.putExtra("USERNAME", un);
        startActivity(intent);
        finish();
    }
}