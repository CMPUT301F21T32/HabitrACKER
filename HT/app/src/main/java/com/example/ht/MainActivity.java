package com.example.ht;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.example.ht.CustomListMain;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {



    TextView usernameLabel;
    TextView nameLabel;
    ListView mainList;
    ArrayList<Habit> habitList = new ArrayList<>();
    ArrayAdapter<Habit> habitAdapter;
    boolean isDeleting = false;
    String username;
    Profile currentUser;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        currentUser = MainUser.getProfile();
        username = currentUser.getUsername();

        habitList = new ArrayList<Habit>();
        mainList = findViewById(R.id.habit_list);
        habitAdapter = new CustomListMain(this, habitList);
        mainList.setAdapter(habitAdapter);

        populateList();
    }



        public void populateList(){
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("Habits")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            habitList.clear();
                            habitAdapter.notifyDataSetChanged();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(currentUser.isFollowing(document.get("username").toString())) {
                                    // Get the attributes from each habit in the database
                                    String title = document.getData().get("name").toString();
                                    String description = document.getData().get("description").toString();
                                    String hour = document.getData().get("hour").toString();
                                    String minute = document.getData().get("minute").toString();
                                    String date = document.getData().get("date").toString();
                                    String selectedDays = document.getData().get("selectedDays").toString();
                                    String username = document.getData().get("username").toString();
                                    String id = document.getId();

                                    // Create new habit and add to the list!
                                    Habit newHabit = new Habit(title, description, selectedDays, hour, minute, date, username, id);
                                    addHabitToList(newHabit);

                                    Log.d("HABIT:", title);
                                }
                            }
                        } else {
                            Log.d("ERROR:", "Error getting documents: ", task.getException());
                        }
                    });


        }

    public void addHabitToList(Habit habit) {
        //habitList.add(habit);
        //habitAdapter.notifyDataSetChanged();
        //Log.d("LIST CHECK", habitList.get(0).getName());

        habitList.add(habit);
        habitAdapter.notifyDataSetChanged();
        Log.d("LIST CHECK", habitList.get(0).getName());

        // update list
    }

    }
