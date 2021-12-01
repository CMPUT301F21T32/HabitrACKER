package com.example.ht;



import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class HabitEvent {
    String habitID;
    String eventID;
    String comment;




    public HabitEvent(String eventID) {
        this.comment = eventID;

    }


    public void fetchEventDetails(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference ref = db.collection("HabitEvents").document(eventID);
        ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){
                        comment = document.get("comment").toString();
                        habitID = document.get("habitID").toString();
                    }
                }
            }
        });
    }

    public String getHabitID() {
        return habitID;
    }

    public void setHabitID(String habitID) {
        this.habitID = habitID;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
