/**
 * ViewHabitActivity
 * @author cole
 *
 * creates a screen that allows you to view the details of a habit
 *
 * This activity should be passed as habitID from the previous activity
 */

package com.example.ht;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ViewHabitActivity extends AppCompatActivity {
    Intent intent;
    String habitID;

    TextView name;
    TextView date;
    TextView description;
    TextView days;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_habit);

        // get habit ID
        intent = getIntent();
        habitID = intent.getStringExtra("HABIT_ID");


        // get view objects
        name = findViewById(R.id.habitViewName);
        date = findViewById(R.id.habitViewDate);
        description = findViewById(R.id.habitViewDescripton);
        days = findViewById(R.id.habitViewDescripton);


        // set screen text to detail of given habit from database
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference ref = db.collection("users").document(habitID);
        ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("HABIT VIEW: ", "Document exists!");
                        name.setText(document.get("name").toString());
                        date.setText("Date Started: " + document.get("date").toString());
                        description.setText("Description:\n" + document.get("description").toString());
                        days.setText(document.get("selectedDays").toString());

//                        String daysText = "";
//
//                        boolean[] daysArray = document.get("selectedDays").toString().arr;





                    } else {
                        Log.d("HABIT VIEW:", "Habit ID not found");
                    }
                }
            }
        });


    }
}