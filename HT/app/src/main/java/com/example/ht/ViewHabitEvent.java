package com.example.ht;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ViewHabitEvent extends AppCompatActivity {
    Button sendComment;
    ImageView eventPhoto;
    ListView eventComments;
    TextView eventDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_event_cell);

        Intent intent= getIntent();
      }


}
