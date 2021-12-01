package com.example.ht;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

/**
 * this is an activity which creates the page where users
 * can see the public habits of the users that they follow
 *
 * @Author Anahita
 */

public class FeedActivity extends AppCompatActivity {

    //initializes variables

    Button profileButton;
    Button searchButton;

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


        profileButton = findViewById(R.id.profile_button);
        searchButton = findViewById(R.id.search_button);



        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToProfile(username);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSearch(username);
            }
        });

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
     * This function looks inside the database,
     * and gets the public habits from users that
     * the current user follows
     */
    public void populateList(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Habits")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        habitList.clear();
                        habitAdapter.notifyDataSetChanged();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            if(document.get("username") != null && currentUser.isFollowing(document.get("username").toString())) {
                                // Get the attributes from each habit in the database
                                String title = document.getData().get("name").toString();
                                String description = document.getData().get("description").toString();
                                String date = document.getData().get("date").toString();
                                String selectedDays = document.getData().get("selectedDays").toString();
                                String username = document.getData().get("username").toString();
                                String openHabit = document.getData().get("open").toString();
                                String id = document.getId();

                                // Create new habit and add to the list!
                                Habit newHabit = new Habit(title, description, selectedDays, date, username, openHabit, id);
                                if (newHabit.getOpenHabit()) {
                                    addHabitToList(newHabit);
                                    Log.d("HABIT:", title);
                                }
                            }
                        }
                    } else {
                        Log.d("ERROR:", "Error getting documents: ", task.getException());
                    }
                });


    }



    /**
     * This function takes a habit, and adds it
     * to the list, updating the adapter.
     * @param habit
     */

    public void addHabitToList(Habit habit) {
        //update the list

        habitList.add(habit);
        habitAdapter.notifyDataSetChanged();
        Log.d("LIST CHECK", habitList.get(0).getName());


    }
}