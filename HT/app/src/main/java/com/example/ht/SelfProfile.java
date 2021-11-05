package com.example.ht;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class SelfProfile extends AppCompatActivity {

    ListView mainList;
    ArrayList<Habit> habitList = new ArrayList<>();
    ArrayAdapter<Habit> habitAdapter;
    //initialize position
    int position;
    ListView v_habitList;
    Button deleteHabitButton;
    FirebaseFirestore db;
    final String TAG = "Sample";
    String username;
    TextView nameLabel;
    TextView usernameLabel;
    ImageButton detailsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.self_profile);
        populateList();
        //initialize list of habits view
        v_habitList = findViewById(R.id.habit_list);
        deleteHabitButton=findViewById(R.id.deleteHabitButton);
        db = FirebaseFirestore.getInstance();

        usernameLabel = findViewById(R.id.username);
        nameLabel = findViewById(R.id.full_name);
        detailsButton = findViewById(R.id.details_button);

        Intent intent = getIntent();
        username = intent.getStringExtra("USERNAME");

        DocumentReference ref = db.collection("users").document(username);
        ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d( TAG, "Document exists!");
                        usernameLabel.setText("@" + username);
                        nameLabel.setText(document.get("name").toString());


                    } else {

                    }
                }
            }
        });




        v_habitList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                position = i;




            }
        });

        deleteHabitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String v_deletehabit = habitList.get(position).getName();

                //db.collection("Cities").document()
                db.collection("Habits")

                        .document(v_deletehabit)
                        .delete()

                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully deleted!" + v_deletehabit);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error deleting document", e);
                            }
                        });
                populateList();
            }
        });
    } //moved it to here


    public void addHabit(Habit habit) {
        habitList.add(habit);
        Log.d("LIST CHECK", habitList.get(0).getName());
        mainList = findViewById(R.id.habit_list);
        habitAdapter = new CustomList(this, habitList);
        mainList.setAdapter(habitAdapter);
        habitAdapter.notifyDataSetChanged();
    }

    public void populateList() {
        final String TAG = "\nADDED:";
//moved this
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Habits")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.d("HERE", "TESSTTTTT");
                            habitList.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String title = document.getData().get("name").toString();
                                String description = document.getData().get("description").toString();
                                String hour = document.getData().get("hour").toString();
                                String minute = document.getData().get("minute").toString();
                                String date = document.getData().get("date").toString();
                                String selectedDays = document.getData().get("selectedDays").toString();
                                String username = document.getData().get("username").toString();
                                String habitID = document.getId();

                                Habit newHabit = new Habit(title, description, selectedDays, hour, minute, date, username, habitID);
                                addHabit(newHabit);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void addHabit(View view) {
        Intent intent = new Intent(this, AddActivity.class);
        intent.putExtra("USERNAME", username);
        startActivity(intent);

        mainList = findViewById(R.id.habit_list);

        habitAdapter = new CustomList(this, habitList);
        mainList.setAdapter(habitAdapter);

        final String TAG = "\nADDED:";

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Habits")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        habitList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String title = document.getData().get("name").toString();
                            String description = document.getData().get("description").toString();
                            String hour = document.getData().get("hour").toString();
                            String minute = document.getData().get("minute").toString();
                            String date = document.getData().get("date").toString();
                            String selectedDays = document.getData().get("selectedDays").toString();
                            String username = document.getData().get("username").toString();
                            String habitID = document.getId();

                            Habit newHabit = new Habit(title, description, selectedDays, hour, minute, date, username, habitID);
                            addHabit(newHabit);
                        }
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });
    }

    // open up habit view activity for the habit at position i
    // in the habit list
    public void viewHabit(int i){
        Intent intent = new Intent(this, ViewHabitActivity.class);
        intent.putExtra("HABITID", habitList.get(i).getHabitID());

        startActivity(intent);

    }
}