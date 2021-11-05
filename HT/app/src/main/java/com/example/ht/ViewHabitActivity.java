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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class ViewHabitActivity extends AppCompatActivity {
    Intent intent;
    Habit habit;

    TextView name;
    TextView date;
    TextView description;
    TextView days;
    ListView mainList;
    Button editButton;
    Button addButton;

    ArrayList<Habit> eventList = new ArrayList<>();
    ArrayAdapter<Habit> eventAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_habit);

        // get habit ID
        intent = getIntent();
        habit = (Habit) intent.getSerializableExtra("habit");


        // get view objects
        name = findViewById(R.id.habitViewName);
        date = findViewById(R.id.habitViewDate);
        description = findViewById(R.id.habitViewDescripton);
        days = findViewById(R.id.habitViewDescripton);

        mainList = findViewById(R.id.event_list);
        editButton = findViewById(R.id.edit_button);
        addButton = findViewById(R.id.addEvent_button);


        // set screen text to detail of given habit
        name.setText(habit.getName());
        date.setText(habit.getDateString());
        description.setText(habit.getDescription());
        days.setText("Scheduled Days: ");

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                    String v_deletehabit = habit.getHabitID();



                    Log.d("TEST!", v_deletehabit);
                    db.collection("Habits")
                            .document(v_deletehabit)
                            .delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("TAG", "City successfully deleted!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("TAG", "Error deleting document", e);
                                }
                            });


                    editHabit(habit);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addHabitEvent(habit);

            }
        });





    }


    /**
     * This function takes a habit, and puts
     * it into a bundle, and passes into the
     * AddActivity intent. The code there checks
     * if the title is the same, and if it is
     * it will apply the changes.
     * @param habit
     */
    public void editHabit(Habit habit) {
        Intent intent = new Intent(this, AddActivity.class);
        intent.putExtra("USERNAME", habit.getUsername());
        intent.putExtra("data", habit);
        startActivity(intent);
        finish();
    }

    /**
     * launches the add habit event activity
     *
     * @param habit the habit which the event will be added to
     */
    public void addHabitEvent(Habit habit) {
        Intent intent = new Intent(this, AddEventActivity.class);
        intent.putExtra("HABITID", habit.getHabitID());
        startActivity(intent);
        finish();
    }







}