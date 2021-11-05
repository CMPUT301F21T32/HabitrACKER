package com.example.ht;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FeedActivity extends AppCompatActivity {

    Button profileButton;

    String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);


        profileButton = findViewById(R.id.profile_button);

        Intent intent = getIntent();
        username = intent.getStringExtra("USERNAME");

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToProfile(username);
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
}