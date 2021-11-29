package com.example.ht;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author Hunter
 *
 * This is an activity for creating a new habit
 * A habit can have a name, description, optional days of the week it occurs,
 * a date, and the option of it being public or private
 *
 */
public class AddActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    EditText habitName;
    EditText habitDesc;
    // List of 7 booleans, storing whether a habit occurs on that day
    List<Boolean> selectedDays = new ArrayList<>(Collections.nCopies(7, false));
    Intent intent;
    // Set the default date to today's date
    String date = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
    boolean openHabit = false;

    /**
     * Creates the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);

        habitName = findViewById(R.id.editText_name);
        habitDesc = findViewById(R.id.editText_desc);

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

        // Private/public button
        ToggleButton openSwitch = findViewById(R.id.openSwitch);
        openSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Change whether the habit is open to the public or not
                openHabit = isChecked;
            }
        });


        // Button for finishing
        Button finish = findViewById(R.id.finishAddActivityButton);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAddActivity();
            }
        });

        Intent intent = getIntent();
        if(intent.getSerializableExtra("data") != null) {
            Habit habit = (Habit) intent.getSerializableExtra("data");
            habitName.setText(habit.getName());
            habitDesc.setText(habit.getDescription());
            Log.d("NOT NULL!", habit.getName());
        }
    }

    /**
     * Used for creating the date picker fragment
     * @param v
     */
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    /**
     * Sets the date to a string representing the date selected in the date picker fragment
     * represented in yyyy/MM/dd
     * @param view
     * @param year year of the date chosen
     * @param month month of the date chosen (starts at 0)
     * @param day day of the date chosen
     */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Months start at january = 0 so add 1 to get the right month number
        date = year + "/" + (month+1) + "/" + day;
    }

    /**
     * This is run when the user presses the finish button
     * This will end the activity if all the selected data meets the requirements
     */
    public void finishAddActivity() {
        Intent intent = getIntent();

        // Create a habit with the data collected
        String name = habitName.getText().toString();
        String desc = habitDesc.getText().toString();
        // Only continue if title and description match character requirements
        boolean isRequirementsMet = true;
        TextView nameRequirementsTextView = ((TextView) findViewById(R.id.textView4));
        TextView descRequirementsTextView = ((TextView) findViewById(R.id.textView5));
        // Change the color if requirements are not met
        if ((name.length() > 20) || (name.length() < 1)) {
            nameRequirementsTextView.setTextColor(Color.RED);
            isRequirementsMet = false;
        }
        else {
            nameRequirementsTextView.setTextColor(Color.GRAY);
        }
        if ((desc.length() > 30) || (desc.length() < 1)) {
            ((TextView) findViewById(R.id.textView5)).setTextColor(Color.RED);
            isRequirementsMet = false;
        }
        else {
            descRequirementsTextView.setTextColor(Color.GRAY);
        }
        if (!isRequirementsMet) return; // Do not continue unless requirements are met
        habitName.getText().clear();
        habitDesc.getText().clear();
        // Put the data into a hashmap
        HashMap<String, String> data = new HashMap<>();
        data.put("name", name);
        data.put("description", desc);
        data.put("date", date);
        data.put("selectedDays", selectedDays.toString());
        data.put("events", "0");
        // Gets the username of the current user
        data.put("username", MainUser.getProfile().getUsername());
        data.put("open", Boolean.toString(openHabit));

        // Put the data into the database
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Habits")
                .document()
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
        Intent i = new Intent(this, SelfProfile.class);
        i.putExtra("USERNAME", intent.getStringExtra("USERNAME"));
        startActivity(i);
        finish();
    }
}