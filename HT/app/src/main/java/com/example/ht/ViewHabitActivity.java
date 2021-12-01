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
import android.widget.AdapterView;
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
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * this creates the page that allows you to view the details of a habit
 *
 * @aurhor cole
 */

public class ViewHabitActivity extends AppCompatActivity {
    Intent intent;
    // adds temporary string for testing, will be replaced in real run
    Habit habit = new Habit("test", "test","[true, true, true, true, true, true, true, true]", "2011/12/2", "test", "true", "test");
    String username;

    TextView name;
    TextView date;
    TextView description;

    ListView events;

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

        if(habit.getDate() != null) {
            date.setText(habit.getDate().getMonth() + "/" + habit.getDate().getDay() + "/" + habit.getDate().getYear());
        }else{
            date.setText("2012/12/12");
        }

        description.setText(habit.getDescription());


        List<Boolean> temp = habit.getSelectedDays();
        Log.d("LOGGING", temp.toString());

        monday.setChecked(temp.get(0));
        tuesday.setChecked(temp.get(1));
        wednesday.setChecked(temp.get(2));
        thursday.setChecked(temp.get(3));
        friday.setChecked(temp.get(4));
        saturday.setChecked(temp.get(5));
        sunday.setChecked(temp.get(6));


        seeEvents(habit, mainList);
        /**
         ViewEventList seeList= new ViewEventList();
         seeList.makeList(habit, mainList);
         mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        seeList.goToEditDelete();
        }
        });
         **/

        mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goToEditDelete();
            }
        });

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

    private void goToEditDelete() {
        Intent intent= new Intent(this, EditDeleteEvent.class);
        startActivity(intent);
        finish();
    }

    /**
     *  This function allows the user to see a list of related habit events
     *  That is, events with the same habitID as said habit
     *
     * @author Jacqueline
     *
     * @param habit
     * @param mainList
     */
    private void seeEvents(Habit habit, ListView mainList) {
        final String HabitID;
        final String[] eventID = new String[1];

        ArrayList<HabitEvent> habitEvent= new ArrayList<>();
        ArrayAdapter<HabitEvent> eventAdapter;

        // Create adapter
        eventAdapter= new PastEventList(this, habitEvent);

        // Connect to HabitEvent object array
        habitEvent= new ArrayList<HabitEvent>();

        // Set adapter
        mainList.setAdapter(eventAdapter);

        // Connect to Firestore database
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Intent intent = getIntent();
        HabitID = intent.getStringExtra("habitID");     // get habitID

        Log.d("TAG", "Not in ViewEventList");
        // Get event list from Firestore
        ArrayList<HabitEvent> finalHabitEvent = habitEvent;
        db.collection("HabitEvents")
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        finalHabitEvent.clear();
                        eventAdapter.notifyDataSetChanged();
                        for(QueryDocumentSnapshot doc : task.getResult()) {
                            Log.d("TAG", "Compare HabitID");
                            if (doc.getData().containsValue(HabitID) ) {
                                // Get HabitEvent values
                                eventID[0] = Objects.requireNonNull(doc.getData().get("habitID")).toString();
                            }

                            // Create new HabitEvent object and add it to the HabitEvent array
                            HabitEvent newEvent= new HabitEvent(eventID[0]);
                            finalHabitEvent.add(newEvent);
                            eventAdapter.notifyDataSetChanged();
                            Log.d("List check", "check");
                        }
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
