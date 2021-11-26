package com.example.ht;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;


import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;


public class Search extends AppCompatActivity {
    String username;
    Button profileButton;
    Button homeButton;


    ImageButton goButton;
    EditText searchBar;
    String searchText;

    ListView list;
    ArrayList<Profile> searchList;
    ArrayAdapter<Profile> searchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchList = new ArrayList<Profile>();
        list = findViewById(R.id.searchList);
        searchAdapter = new ProfileList(this, searchList);
        list.setAdapter(searchAdapter);


        profileButton = findViewById(R.id.searchProfile_button);
        homeButton = findViewById(R.id.searchHome_button);

        searchBar = findViewById(R.id.searchTextBox);
        goButton = findViewById(R.id.searchGoButton);



        list = findViewById(R.id.searchList);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                goToUser(searchList.get(i));

            }
        });


        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchText = searchBar.getText().toString();
                if(searchText.length() > 0){
                    populateList();
                }
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToProfile();
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToFeed();
            }
        });
    }

    /**
     * This function takes a Profile, and adds it
     * to the list, updating the adapter.
     * Unfortunately I could not just add the item in
     * populateList() because it wont work.
     * REUSED FROM SELF PROFILE
     * @param profile
     */
    public void addProfileToList(Profile profile) {
        searchList.add(profile);
        Log.d("LIST CHECK", searchList.get(0).getName());


        // update list
        searchAdapter.notifyDataSetChanged();
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
        db.collection("users")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        searchList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            if(document.get("username").toString().contains(searchText) ||
                                    document.get("name").toString().contains(searchText)) {
                                // Get the attributes from each habit in the database
                                String name = document.get("name").toString();
                                String username = document.getData().get("username").toString();
                                String password = "*********";
                                String following = document.get("following").toString();
                                String followers = document.get("followers").toString();


                                // Create new habit and add to the list!
                                Profile newProfile = new Profile(username, password, name,following, followers);
                                addProfileToList(newProfile);

                                Log.d("Profile:", name);
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

    // starts the main feed activity
    private void goToFeed(){
        Intent intent = new Intent(this, FeedActivity.class);
        startActivity(intent);
        finish();
    }

    //opens user profile
    private void goToUser(Profile user){
        Intent intent = new Intent(this, OtherUserProfile.class);
        intent.putExtra("USER", user);
        intent.putExtra("PARENT", "search");


        startActivity(intent);

    }
}