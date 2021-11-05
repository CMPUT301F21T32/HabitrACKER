package com.example.ht;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class HabitEvent {
    String habitID;
    String description;
    String hour;
    String minute;
    String name;
    String username;


    public HabitEvent(String habitID, String description, String hour, String minute, String name, String username) {
        this.habitID= habitID;
        this.description= description;
        this.hour= hour;
        this.minute= minute;
        this.name= name;
        this.username= username;
    }

    public String getHabitID(){ return habitID; }
    public void setHabitID(String habitID) { this.habitID= habitID; }

    public String getHabitDescription() { return description; }
    public void setHabitDescription(String description) { this.description= description; }

    public String getHour() { return hour; }
    public void setHour(String hour) { this.hour= hour; }

    public String getMinute() { return minute; }
    public void setMinute(String minute) { this.minute= minute; }

    public String getEventName() { return name; }
    public void setEventName(String name) { this.name= name;}

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username= username; }

    public void fetchEventDetails(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference ref = db.collection("Habits").document(habitID);
        ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){
                        description = document.get("description").toString();
                        hour = document.get("hour").toString();
                        minute = document.get("minute").toString();
                        name = document.get("name").toString();
                        username = document.get("username").toString();
                    }
                }
            }
        });
    }

}