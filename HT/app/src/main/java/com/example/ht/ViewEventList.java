package com.example.ht;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ViewEventList extends AppCompatActivity {
    String HabitID;
    String eventID;
    String eventName;
    String eventDescription;
    String eventComment;

    ListView eventList;
    ArrayList<HabitEvent> habitEvent= new ArrayList<>();
    ArrayAdapter<HabitEvent> eventAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_habit);

        // Connect to Firestore database
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Intent intent = getIntent();
        HabitID = intent.getStringExtra("habitID");     // get habitID

        // Connect to activity page
        eventList= (ListView) findViewById(R.id.event_list);

        // Set adapter
        eventAdapter= new PastEventList(this, habitEvent);

        // Connect to HabitEvent object array
        habitEvent= new ArrayList<HabitEvent>();

        //
        eventList.setAdapter(eventAdapter);

        // Get event list from Firestore
        db.collection("HabitEvents")
                .get()
                .addOnCompleteListener(task -> {
                   if(task.isSuccessful()){
                       habitEvent.clear();
                       eventAdapter.notifyDataSetChanged();
                       for(QueryDocumentSnapshot doc : task.getResult()) {
                           if (doc.getData().get("habitID") == HabitID) {
                               // Get HabitEvent values
                               String eventID= doc.getData().get("habitID").toString();
                               String eventName= doc.getData().get("name").toString();
                               String eventComment= doc.get("comment").toString();
                               String eventDescription= doc.get("reason").toString();
                           }

                           // Create new HabitEvent object and add it to the HabitEvent array
                           HabitEvent newEvent= new HabitEvent(eventID, eventName, eventDescription, eventComment);
                           habitEvent.add(newEvent);
                           eventAdapter.notifyDataSetChanged();
                           Log.d("List check", habitEvent.get(0).gethabitID());
                       }
                   }
                });



        /**
        db.collection("HabitEvents")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    habitEvent.clear();
                    eventAdapter.notifyDataSetChanged();
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        // Populate the list
                        if(!queryDocumentSnapshots.isEmpty()){
                            for(QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                                if (doc.getData().get("habitID") == HabitID) {
                                    doc.getData().get("habitID");
                                    HabitEvent newEvent = doc.toObject(HabitEvent.class);        // convert doc to HabitEvent object
                                    habitEvent.add(newEvent);                                   // add to list
                                }
                            }

                            // Connect to list to display in app
                            ArrayAdapter<HabitEvent> eventAdapter= new ArrayAdapter<HabitEvent>(getApplicationContext(), R.layout.event_cell, habitEvent);
                            eventAdapter.notifyDataSetChanged();
                            eventList.setAdapter(eventAdapter);

                        }
                    }
                });
                    **/

        /**
        db.collection("HabitEvents")
                .get()
                .addOnCompleteListener(task -> {
                    habitEvent.clear();
                    eventAdapter.notifyDataSetChanged();
                    for(QueryDocumentSnapshot doc : task.getResult()){
                        if (HabitID==doc.get("habitID").toString()){
                            // Get HabitEvent values
                            String eventID= doc.getData().get("habitID").toString();
                            String eventName= doc.getData().get("name").toString();
                            String eventComment= doc.get("comment").toString();
                            String eventDescription= doc.get("reason").toString();
                        }

                        // Create new HabitEvent object and add it to the HabitEvent array
                        HabitEvent newEvent= new HabitEvent(eventID, eventName, eventDescription, eventComment);
                        habitEvent.add(newEvent);
                    }

                    // Connect to list to display in app
                    eventAdapter.notifyDataSetChanged();
                    Log.d("LIST CHECK", habitEvent.get(0).getName());

                });
        **/


        // When user clicks on event, go to details to edit/delete
        eventList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go to EditDeleteEvent class
                goToEditDelete();
            }
        });

    }

    public void goToEditDelete(){
        // Allow user to edit or delete the habit event
        Intent intent = new Intent(this, FeedActivity.class);
        startActivity(intent);
    }
}
