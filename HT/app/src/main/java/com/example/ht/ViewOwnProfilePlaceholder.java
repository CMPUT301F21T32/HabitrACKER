package com.example.ht;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ViewOwnProfilePlaceholder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_own_profile_placeholder);
        Button addActivity = findViewById(R.id.addActivityButton);
        addActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAddActivity();
            }
        });
    }

    private void goToAddActivity(){
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
    }
}
