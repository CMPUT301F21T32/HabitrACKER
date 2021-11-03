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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class AddActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    EditText habitName;
    EditText habitDesc;
    // List of 7 booleans, storing whether a habit occurs on that day
    List<Boolean> selectedDays = new ArrayList<>(Collections.nCopies(7, false));
    TimePicker time;
    String date = new SimpleDateFormat("yyyy/MM/dd").format(new Date());

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
    public void onDateSet(DatePicker view, int year, int month, int day) {
        //do some stuff for example write on log and update TextField on activity
        date = year + "/" + month + "/" + day;
    }

    public void finishAddActivity() {
        // Create a habit with the data collected
        String name = habitName.getText().toString();
        String desc = habitDesc.getText().toString();
        if (name.length() > 20) return;
        if (desc.length() > 30) return;
        habitName.getText().clear();
        habitDesc.getText().clear();
        String hour = Integer.toString(time.getCurrentHour());
        String minute = Integer.toString(time.getCurrentMinute());
        HashMap<String, String> data = new HashMap<>();
        data.put("name", name);
        data.put("description", desc);
        data.put("hour", hour);
        data.put("minute", minute);
        data.put("date", date);
        data.put("selectedDays", selectedDays.toString());
        data.put("username", "Hunter3");

        // Put the data into the database
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Habits")
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
