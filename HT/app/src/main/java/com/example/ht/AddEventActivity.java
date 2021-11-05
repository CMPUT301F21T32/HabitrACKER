package com.example.ht;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class AddEventActivity extends AppCompatActivity {
    EditText habitEventDescription;
    Button addHabitEventButton;
    String comment;
    String habitID;
    String description;
    String hour;
    String minute;
    String name;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        habitEventDescription = findViewById(R.id.comment);
        addHabitEventButton = findViewById(R.id.add_habit_event);

        comment = habitEventDescription.getText().toString();
        // also the photograph and location

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        habitID = intent.getStringExtra("HABITID");

        DocumentReference ref = db.collection("Habits").document(habitID);
        ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){
                        description = document.get("description").toString();
                        hour = document.get("hour").toString();
                        minute = document.get("minute").toString();
                        name = document.get("name").toString();
                        username = document.get("username").toString();
                    }
                }
            }
        });
        addHabitEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                habitEventDescription.getText().clear();
                HashMap<String, String> data = new HashMap<>();
                data.put("habitID", habitID);
                data.put("name", name);
                data.put("comment", comment);
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("HabitEvents")
                        .document()
                        .set(data)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void avoid){
                                Log.d("AddHabitEvent", "HabitEventAddedSuccessfully");
                                goToProfile();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("AddHabitEvent", "Couldn't be added");
                            }
                        });
            }

        });
    }

    /**
     * returns app to profile activity
     */
    public void goToProfile(){
        Intent intent = new Intent(this, SelfProfile.class);
        intent.putExtra("USERNAME", username);

        startActivity(intent);
        finish();
    }


}