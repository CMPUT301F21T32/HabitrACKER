// CODED BY JOSH
package com.example.ht;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.io.Serializable;
import java.util.ArrayList;

public class SelfProfile extends AppCompatActivity {

    ListView mainList;
    ArrayList<Habit> habitList = new ArrayList<>();
    ArrayAdapter<Habit> habitAdapter;
    boolean isDeleting = false;
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

        Button deleteButton = findViewById(R.id.delete_button);

        // Setting up list item click
        mainList = findViewById(R.id.habit_list);
        mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * This function sets an onItemClickAdapter for the listview
             * NOTE: THIS ONLY WORKS WHEN THE LINERAR LAYOUT OF THE CELL
             * CONTENT HAS android:descendantFocusability = "blocksDescendants"
             * After the button (FOLLOW REQUESTS FOR NOW) is clicked, you
             * are in deletion mode. You can now click on any list item to
             * delete it. After it is deleted from the database, it is removed
             * from the list, and we update the adapter accordingly.
             * This is all inside a condition
             * @param adapterView
             * @param view
             * @param i
             * @param l
             */
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(isDeleting) {
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    String v_deletehabit = habitList.get(i).getHabitID();
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
                    removeItemInList(i);
                }
                else {
                    editHabit(habitList.get(i));
                }
            }
        });

        /**
         * This sets up the button click listener.
         * When the button is clicked, it switches the
         * boolean delete mode (T=Deleting, F=Not Deleting)
         * and switches the button text accordingly.
         */
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isDeleting = !isDeleting;
                if(isDeleting)
                    deleteButton.setText("Deleting");
                else
                    deleteButton.setText("Delete habit");
            }
        });
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
                            String id = document.getId();

                            // Create new habit and add to the list!
                            Habit newHabit = new Habit(title, description, selectedDays, hour, minute, date, username, id);
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
    }

    /**
     * Given an index, will remove that item
     * from the list, and update the adapter
     * @param index
     */
    public void removeItemInList(int index) {
        if (index >= 0) {
            habitList.remove(index);
            habitAdapter.notifyDataSetChanged();
        }
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