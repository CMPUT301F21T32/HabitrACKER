package com.example.ht;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class FollowRequestActivity extends AppCompatActivity {
    ListView list;
    ImageButton back;
    ArrayList<String> requestList;
    ArrayAdapter<String> requestAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_request);


        requestList = new ArrayList<String>();
        list = findViewById(R.id.requestList);
        requestAdapter = new RequestList(this, requestList);
        list.setAdapter(requestAdapter);

        populateList();

        back = findViewById(R.id.requestCloseButton);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToProfile();
            }
        });
    }


    /**
     * This function takes a Profile, and adds it
     * to the list, updating the adapter.
     * Unfortunately I could not just add the item in
     * populateList() because it wont work.
     * REUSED FROM SELF PROFILE
     * @param user
     */
    public void addRequestToList(String user) {
        requestList.add(user);
        Log.d("LIST CHECK", requestList.get(0));


        // update list
        requestAdapter.notifyDataSetChanged();
    }


    /**
     * This function looks inside the database,
     * and gets the data related to users that match
     * the current search and then
     * stores those values into a Profile object
     * which then adds to the searchList
     * REUSED FROM SELF PROFILE
     */
    public void populateList() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Follow Requests")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        requestList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            if(document.get("to").toString().equals(MainUser.getProfile().getUsername())) {

                                addRequestToList(document.get("from").toString());

                            }
                        }
                    } else {
                        Log.d("ERROR:", "Error getting documents: ", task.getException());
                    }
                });


    }

    //starts the profile activity
    private void goToProfile(){
        Intent intent = new Intent(this, SelfProfile.class);

        startActivity(intent);
        finish();
    }
}