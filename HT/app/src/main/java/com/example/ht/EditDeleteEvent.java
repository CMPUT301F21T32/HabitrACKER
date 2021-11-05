package com.example.ht;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Document;

import java.util.HashMap;

public class EditDeleteEvent extends AppCompatActivity {
    String HabitEventID;
    Button deleteButton;
    Button saveButton;
    EditText habitName;
    EditText habitReason;
    EditText seeComments;
    EditText location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_details);

        Intent intent= getIntent();
        HabitEventID= intent.getStringExtra("HabitEventID");

        saveButton= findViewById(R.id.save_button);
        deleteButton= findViewById(R.id.delete_button);
        habitName= (EditText) findViewById(R.id.habit_name);
        habitReason= (EditText) findViewById(R.id.habit_reason);
        seeComments= (EditText) findViewById(R.id.habit_comment);
        location= (EditText) findViewById(R.id.event_location);

        // delete button
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { deleteEvent(); }
        });

        // edit/save button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEvent();
            }
        });
    }

    private void deleteEvent() {
        // Update Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        HashMap<String, String> data = new HashMap<>();

        // get list from Firestore
        DocumentReference events= db.collection("HabitEvents").document(HabitEventID);

        // delete
        events.collection("HabitEvents")
                .document(HabitEventID)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("DeleteActivity", "Habit Event has been deleted");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("DeleteActivity", "Error deleting Document");
                    }
                });
    }

    private void saveEvent() {
        // get values currently in EditText fields
        String textName= habitName.getText().toString();
        String textReason= habitReason.getText().toString();
        String textComment= seeComments.getText().toString();

        if (seeComments.length() > 20){
            return;
        }

        // Add to Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        HashMap<String, String> data = new HashMap<>();
        data.put("HabitID", HabitEventID);
        data.put("HabitName", textName);
        data.put("Reason", textReason);
        data.put("Comment", textComment);

        // Get description from Habits collection
        DocumentReference fromHabits = db.collection("Habits").document(HabitEventID);
        String description= fromHabits.get().toString();

        // Put into Firestore
        db.collection("HabitEvents")
                .document(HabitEventID)
                .set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // These are a method which gets executed when the task is succeeded
                        Log.d("AddActivitySample", "Habit Event has been saved");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // These are a method which gets executed if there’s any problem
                        Log.d("AddActivitySample", "Error saving Habit Event" + e.toString());
                    }
                });
    }
}
