package com.example.ht;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

public class EditFragment extends Fragment {
    // Call to event list or database

    private EditText habitName;
    private EditText habitReason;
    private EditText seeComments;
    private EditText eventLocation;      //For later

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Intent intent;

        /**
        View view= new View(R.layout.event_details);

        habitName= view.findViewById(R.id.habit_name);
        habitReason= view.findViewById(R.id.habit_reason);
        seeComments= view.findViewById(R.id.habit_comment);
        eventLocation= view.findViewById(R.id.event_location);
        **/


    }
}
