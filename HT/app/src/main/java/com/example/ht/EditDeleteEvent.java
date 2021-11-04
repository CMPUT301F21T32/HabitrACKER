package com.example.ht;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

public class EditDeleteEvent extends AppCompatActivity {
    EditDeleteEvent event;

    Button deleteButton;
    Button editButton;
    EditText habitName;
    EditText habitReason;
    EditText seeComments;
    ArrayAdapter<String> habitEventAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_details);

        editButton= findViewById(R.id.edit_button);
        deleteButton= findViewById(R.id.delete_button);
        habitName= (EditText) findViewById(R.id.habit_name);
        habitReason= (EditText) findViewById(R.id.habit_reason);
        seeComments= (EditText) findViewById(R.id.habit_comment);

        // delete button
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Wait for code
                deleteEvent();
            }
        });

        // edit button
        editButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // Wait for code
                editEvent();
            }
        });

    }

    private void deleteEvent() {

    }


    private void editEvent() {
        //Maybe

        Fragment edit= new EditFragment();

        String textName= habitName.getText().toString();
        String textReason= habitReason.getText().toString();
        String textComment= seeComments.getText().toString();

        habitName.getText().clear();
        habitReason.getText().clear();
        seeComments.getText().clear();

        //event.setHabitTitle(textName);
        //event.setHabitDescription(textReason);
        //event.setComments(textComment);

    }
}
