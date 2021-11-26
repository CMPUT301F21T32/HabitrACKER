package com.example.ht;



import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public abstract class HabitEvent {
    String habitID;
    String description;
    String hour;
    String minute;
    String name;
    String username;


    public HabitEvent(String habitID) {
    }


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