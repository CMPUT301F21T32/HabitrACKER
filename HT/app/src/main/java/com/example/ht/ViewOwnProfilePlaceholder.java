package com.example.ht;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ViewOwnProfilePlaceholder extends AppCompatActivity {

    private static final String TAG = "Read a users habits";

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

        ArrayList<Habit> habits = new ArrayList<Habit>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document("Hunter2").collection("Habits")
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        // get day of the week
                        int dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
                        for (QueryDocumentSnapshot doc : task.getResult()) {
                            Log.d(TAG, doc.getId() + " => " + doc.getData());
                            String name = (String) doc.get("name");
                            String desc = (String) doc.get("description");
                            String date = (String) doc.getData().get("date");
                            String minute = (String) doc.getData().get("minute");
                            String hour = (String) doc.getData().get("hour");
                            String selectedDays = (String) doc.getData().get("selectedDays");
                            Habit habit = new Habit(name, desc, selectedDays, hour, minute, date);
                            habits.add(habit);
                        }
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                }
            });
    }

    private void goToAddActivity(){
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
    }

}
