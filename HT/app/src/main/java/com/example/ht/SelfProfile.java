// CODED BY JOSH
package com.example.ht;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class SelfProfile extends AppCompatActivity {

    ListView mainList;
    ArrayList<Habit> habitList = new ArrayList<>();
    ArrayAdapter<Habit> habitAdapter;
    // Declarations for the xml list, list that contains habit items, and the adapter for the array

    /**
     * This starts the activity, and inflates the self_profile
     * layout. NOTE: in AndroidManifest.xml, I set this screen
     * to be the main (first) screen, to change this,
     * take out the <activity></activity> with .SelfProfile
     * OUTSIDE of the <intent-filter></intent-filter>
     * you also might have to change the android:parent tag
     * on some of the other activities.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.self_profile);
        populateList();
    }

    /**
     * This function takes a habit, and adds it
     * to the list, updating the adapter.
     * Unfortunately I could not just add the item in
     * populateList() because it wont work.
     * @param habit
     */
    public void addHabitToList(Habit habit) {
        habitList.add(habit);
        Log.d("LIST CHECK", habitList.get(0).getName());

        mainList = findViewById(R.id.habit_list);
        //xml list reference

        habitAdapter = new CustomList(this, habitList);
        mainList.setAdapter(habitAdapter);
        habitAdapter.notifyDataSetChanged();
        // update list
    }

    /**
     * This function looks inside the database,
     * and gets the title, description, hour,
     * minute, date, selected days, and username
     * from the habit in the database, and
     * stores those values into a habit object
     * which then adds to the habitList
     */
    public void populateList() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Habits")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        habitList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {

                            // Get the attributes from each habit in the database
                            String title = document.getData().get("name").toString();
                            String description = document.getData().get("description").toString();
                            String hour = document.getData().get("hour").toString();
                            String minute = document.getData().get("minute").toString();
                            String date = document.getData().get("date").toString();
                            String selectedDays = document.getData().get("selectedDays").toString();
                            String username = document.getData().get("username").toString();

                            // Create new habit and add to the list!
                            Habit newHabit = new Habit(title, description, selectedDays, hour, minute, date, username);
                            addHabitToList(newHabit);

                            Log.d("HABIT:", title);
                        }
                    } else {
                        Log.d("ERROR:", "Error getting documents: ", task.getException());
                    }
                });
    }

    /**
     * This function starts an intent (AddActivity)
     * and adds the habit to the database. This
     * is called when "Add Habit" Button is
     * tapped.
     * @param view
     */
    public void addHabit(View view) {
        // Start AddActivity Page
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);

        Log.d("POPULATING:", "\n");
    }

    /**
     * Overrides onResume to update the list
     * everytime the page is resumed.
     */
    @Override public void onResume() {
        super.onResume();
        populateList();
    }
}