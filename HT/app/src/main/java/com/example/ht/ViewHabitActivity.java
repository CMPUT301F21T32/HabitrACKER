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
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * this creates the page that allows you to view the details of a habit
 */

public class ViewHabitActivity extends AppCompatActivity {
    Intent intent;
    Habit habit;
    String username;

    TextView name;
    TextView date;
    TextView description;

    ToggleButton monday;
    ToggleButton tuesday;
    ToggleButton wednesday;
    ToggleButton thursday;
    ToggleButton friday;
    ToggleButton saturday;
    ToggleButton sunday;

    ListView mainList;
    Button editButton;
    Button addButton;

    Button home;
    Button search;
    Button profile;

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

        monday = findViewById(R.id.M);
        tuesday = findViewById(R.id.T);
        wednesday = findViewById(R.id.W);
        thursday = findViewById(R.id.Th);
        friday = findViewById(R.id.F);
        saturday = findViewById(R.id.Sa);
        sunday = findViewById(R.id.Su);



        mainList = findViewById(R.id.event_list);
        editButton = findViewById(R.id.edit_button);
        addButton = findViewById(R.id.addEvent_button);

        home = findViewById(R.id.eventHome);
        profile = findViewById(R.id.eventProfile_button);
        search = findViewById(R.id.eventSearch);


        // set screen text to detail of given habit
        name.setText(habit.getName());
        date.setText(habit.getDate().getMonth() + "/" + habit.getDate().getDay() + "/" + habit.getDate().getYear());
        description.setText(habit.getDescription());


        List<Boolean> temp = habit.getSelectedDays();

        monday.setChecked(temp.get(0));
        tuesday.setChecked(temp.get(1));
        wednesday.setChecked(temp.get(2));
        thursday.setChecked(temp.get(3));
        friday.setChecked(temp.get(4));
        saturday.setChecked(temp.get(5));
        sunday.setChecked(temp.get(6));

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

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToHome(username);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToProfile(username);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSearch(username);
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


    /**
     * starts profile activity
     * @param un
     */
    private void goToProfile(String un){
        Intent intent = new Intent(this, SelfProfile.class);

        startActivity(intent);
        finish();
    }

    /**
     * starts search activity
     * @param un u
     */
    private void goToSearch(String un){
        Intent intent = new Intent(this, Search.class);

        startActivity(intent);
        finish();
    }

    /**
     * starts home feed activity
     * @param un
     */
    private void goToHome(String un){
        Intent intent = new Intent(this, FeedActivity.class);
        startActivity(intent);;
        finish();
    }

}