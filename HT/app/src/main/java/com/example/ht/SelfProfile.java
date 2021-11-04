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
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
    Button editHabitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.self_profile);
        populateList();
        //initialize list of habits view
        v_habitList = findViewById(R.id.habit_list);
        deleteHabitButton=findViewById(R.id.deleteHabitButton);
        db = FirebaseFirestore.getInstance();
        editHabitButton=findViewById(R.id.edit_btn);





        v_habitList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG,"onclicklistener1");
                position = i;

                Log.d(TAG, "position" + position);


            }
        });



        //keeps deleting first item ......worked on with ta to try to fix
        deleteHabitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // System.out.print("position"+position);
                Log.d(TAG, "position on click delete" + position);
                String v_deletehabit = habitList.get(position).getName();

                //db.collection("Cities").document()
                db.collection("Habits")

                        .document(v_deletehabit)
                        .delete()

                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully deleted!" + v_deletehabit + position);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error deleting document", e);
                            }
                        });
//populateList();
habitList.remove(position);
                habitAdapter.notifyDataSetChanged();
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

                                Habit newHabit = new Habit(title, description, selectedDays, hour, minute, date, username);
                                addHabit(newHabit);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void editHabit(View view){
        Intent intent = new Intent(this, EditActivity.class);
        startActivity(intent);
    }
    public void addHabit(View view) {
        Intent intent = new Intent(this, AddActivity.class);
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

                            Habit newHabit = new Habit(title, description, selectedDays, hour, minute, date, username);
                            addHabit(newHabit);
                        }
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });
    }
}