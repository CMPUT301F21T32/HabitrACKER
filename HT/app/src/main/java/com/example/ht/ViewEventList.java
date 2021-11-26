package com.example.ht;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ViewEventList extends AppCompatActivity {
    String habitID;
    ListView eventList;
    ArrayList<HabitEvent> habitevent= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_habit);

        // Connect to Firestore database
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Intent intent = getIntent();
        habitID = intent.getStringExtra("HabitID");

        // Connect to activity page
        eventList= (ListView) findViewById(R.id.event_list);

        // Connect to HabitEvent object
        habitevent= new ArrayList<>();

        // Get event list from Firestore
        db.collection("HabitEvents")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        // Populate the list
                        if(!queryDocumentSnapshots.isEmpty()){
                            for(QueryDocumentSnapshot doc : queryDocumentSnapshots){
                                HabitEvent newEvent= doc.toObject(HabitEvent.class);        // convert doc to HabitEvent object
                                habitevent.add(newEvent);                                   // add to list
                            }
                        }
                    }
                });

        // Connect to list to display in app
        for (int i=0; i<habitevent.size(); i++){
            ArrayAdapter<HabitEvent> eventAdapter= new ArrayAdapter<HabitEvent>(habitevent.get(i).habitID);
            eventAdapter.notifyDataSetChanged();
            eventList.setAdapter(eventAdapter);
        }

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
