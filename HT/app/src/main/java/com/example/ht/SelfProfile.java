package com.example.ht;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class SelfProfile extends AppCompatActivity {

    ListView mainList;
    ArrayList<Habit> habitList = new ArrayList<>();
    ArrayAdapter<Habit> habitAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.self_profile);
        populateList();
    }

    public void populateList() {
        final String TAG = "\nADDED:";

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Habits")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.d("HERE", "TESSTTTTT");
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String title = document.getData().get("name").toString();
                                String description = document.getData().get("description").toString();
                                String hour = document.getData().get("hour").toString();
                                String minute = document.getData().get("minute").toString();
                                String date = document.getData().get("date").toString();
                                String selectedDays = document.getData().get("selectedDays").toString();
                                String username = document.getData().get("username").toString();

                                Habit newHabit = new Habit(title, description, selectedDays, hour, minute, date, username);
                                addHabit(newHabit);
                                Log.d("ADDED TITLE", title);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        //Log.d("LIST:", habitList.get(0).getName());
        mainList = findViewById(R.id.habit_list);
        habitAdapter = new CustomList(this, habitList);
        mainList.setAdapter(habitAdapter);
        habitAdapter.notifyDataSetChanged();
    }

    public void addHabit(View view) {
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);

        mainList = findViewById(R.id.habit_list);

        habitAdapter = new CustomList(this, habitList);
        mainList.setAdapter(habitAdapter);

        final String TAG = "\nADDED:";

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Habits")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String title = document.getData().get("name").toString();
                            String description = document.getData().get("description").toString();
                            String hour = document.getData().get("hour").toString();
                            String minute = document.getData().get("minute").toString();
                            String date = document.getData().get("date").toString();
                            String selectedDays = document.getData().get("selectedDays").toString();
                            String username = document.getData().get("username").toString();

                            Habit newHabit = new Habit(title, description, selectedDays, hour, minute, date, username);
                            habitList.add(newHabit);
                            //Log.d(TAG, newHabit.getName());
                            Log.d(TAG, title);
                        }
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });
        Log.d("\nLIST:", habitList.get(0).getName());
        habitAdapter.notifyDataSetChanged();
    }

    public void addHabit(Habit habit) {
        habitList.add(habit);
    }
}