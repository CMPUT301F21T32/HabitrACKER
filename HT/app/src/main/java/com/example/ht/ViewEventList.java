package com.example.ht;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * gets the information from the firestore database so it can be stored in the app
 *
 * @aurhor jacqueline
 */

public class ViewEventList extends AppCompatActivity {
    String HabitID;
    String eventID;

    ListView eventList;
    ArrayList<HabitEvent> habitEvent= new ArrayList<>();
    ArrayAdapter<HabitEvent> eventAdapter;

    public void makeList(Habit habit, ListView eventList){
        //setContentView(R.layout.activity_view_habit);

        // Connect to activity page
        //eventList= (ListView) findViewById(R.id.event_list);

        // Create adapter
        eventAdapter= new PastEventList(this, habitEvent);

        // Connect to HabitEvent object array
        habitEvent= new ArrayList<HabitEvent>();

        // Set adapter
        eventList.setAdapter(eventAdapter);


        // Connect to Firestore database
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Intent intent = getIntent();
        HabitID = intent.getStringExtra("habitID");     // get habitID

        // Get event list from Firestore
        db.collection("HabitEvents")
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        habitEvent.clear();
                        eventAdapter.notifyDataSetChanged();
                        for(QueryDocumentSnapshot doc : task.getResult()) {
                            if (doc.contains(HabitID)) {
                                // Get HabitEvent values
                                String eventID= Objects.requireNonNull(doc.getData().get("habitID")).toString();
                            }

                            // Create new HabitEvent object and add it to the HabitEvent array
                            HabitEvent newEvent= new HabitEvent(eventID);
                            habitEvent.add(newEvent);
                            eventAdapter.notifyDataSetChanged();
                            Log.d("List check", "check");
                        }
                    }
                });

    }

    public void goToEditDelete(){
        // Allow user to edit or delete the habit event
        Intent intent = new Intent(this, EditDeleteEvent.class);
        startActivity(intent);
        finish();
    }


}
