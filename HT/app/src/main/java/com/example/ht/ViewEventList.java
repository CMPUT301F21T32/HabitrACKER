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
import java.util.Objects;

/**
 * gets the information from the firestore database so it can be stored in the app
 *
 * @aurhor jacqueline
 */

public class ViewEventList extends AppCompatActivity {
    String HabitID;
    String eventID;
    String eventName;
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
                           if (doc.get("habitID").equals(HabitID)) {
                               // Get HabitEvent values
<<<<<<< HEAD
                               String eventID= Objects.requireNonNull(doc.getData().get("habitID")).toString();
                               String eventName= Objects.requireNonNull(doc.getData().get("name")).toString();
                               String eventComment= Objects.requireNonNull(doc.get("comment")).toString();
=======
                               String eventID= doc.getData().get("habitID").toString();
                               String eventName= doc.getData().get("name").toString();
                               String eventComment= doc.get("comment").toString();
>>>>>>> 6b91c6edf0568e0d8f17c86a7d8c6b3adce22d4b
                           }

                           // Create new HabitEvent object and add it to the HabitEvent array
                           HabitEvent newEvent= new HabitEvent(eventID, eventName, eventComment);
                           habitEvent.add(newEvent);
                           eventAdapter.notifyDataSetChanged();
                           Log.d("List check", "check");
                       }
                   }
                });

<<<<<<< HEAD
=======


>>>>>>> 6b91c6edf0568e0d8f17c86a7d8c6b3adce22d4b
        // When user clicks on event, go to details to edit/delete
        eventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goToEditDelete();
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
