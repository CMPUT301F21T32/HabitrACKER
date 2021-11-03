package com.example.ht;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class AddActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    EditText habitName;
    EditText habitDesc;
    // List of 7 booleans, storing whether a habit occurs on that day
    List<Boolean> selectedDays = new ArrayList<>(Collections.nCopies(7, false));
    TimePicker time;
    String date; // Doesnt work

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);

        habitName = findViewById(R.id.editText_name);
        habitDesc = findViewById(R.id.editText_desc);
        time = (TimePicker) findViewById(R.id.timePicker);

        // Ids for the 7 toggle buttons for each day
        int[] dayButtonIds = {R.id.toggleSun, R.id.toggleMon, R.id.toggleTue, R.id.toggleWed,
                              R.id.toggleThu, R.id.toggleFri, R.id.toggleSat};
        for (int i = 0; i < 7; i++) {
            ToggleButton toggle = (ToggleButton) findViewById(dayButtonIds[i]);
            int finalI = i;
            toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // Change whether the habit occurs on a day if that day's button's toggled
                    selectedDays.set(finalI, isChecked);
                }
            });
        }

        // Button for finishing
        Button finish = findViewById(R.id.finishAddActivityButton);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAddActivity();
            }
        });
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) { // what should be done when a date is selected
        date = year + "/" + monthOfYear + "/" + dayOfMonth;
    }

    public void finishAddActivity() {
        // Create a habit with the data collected
        String name = habitName.getText().toString();
        String desc = habitDesc.getText().toString();
        habitName.getText().clear();
        habitDesc.getText().clear();
        int hour = time.getCurrentHour();
        int minute = time.getCurrentMinute();
        Habit habit = new Habit(name, desc, selectedDays, hour, minute, date, "Hunter");
        HashMap<String, Habit> data = new HashMap<>();
        data.put("Habit", habit);

        // Put the data into the database
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                // "Hunter" is a hardcoded username. Will change in the future
                .document("Hunter")
                .collection("Habits")
                .document(name)
                .set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // These are a method which gets executed when the task is succeeded
                        Log.d("AddActivitySample", "Habit has been added successfully!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // These are a method which gets executed if there’s any problem
                        Log.d("AddActivitySample", "Habit could not be added!" + e.toString());
                    }
                });
        // Go to the previous activity
        finish();
    }

}