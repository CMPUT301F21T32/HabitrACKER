package com.example.ht;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

/**
 * This created the page that allows users to see a list of all
 * the users they follow/are following
 *
 * @author cole
 */

public class ViewFollowActivity extends AppCompatActivity {
    String mode;


    ListView list;
    ArrayList<Profile> followList;
    ArrayAdapter<Profile> followAdapter;

    ImageButton back;
    TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_follow);

        Intent intent = getIntent();
        mode = intent.getStringExtra("MODE");


        followList = new ArrayList<Profile>();
        list = findViewById(R.id.follow_list);
        followAdapter = new ProfileList(this, followList);
        list.setAdapter(followAdapter);

        back = findViewById(R.id.followViewBack);
        title = findViewById(R.id.followViewTitle);

        title.setText(mode);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToProfile();
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                goToUser(followList.get(i));

            }
        });



        populateList();
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
        followList.add(profile);
        Log.d("LIST CHECK", followList.get(0).getName());


        // update list
        followAdapter.notifyDataSetChanged();
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

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            if(mode.equals("following")) {
                                if (MainUser.getProfile().isFollowing(document.get("username").toString())) {
                                    // Get the attributes from each habit in the database
                                    String name = document.get("name").toString();
                                    String username = document.getData().get("username").toString();
                                    String password = "*********";
                                    String following = document.get("following").toString();
                                    String followers = document.get("followers").toString();


                                    // Create new habit and add to the list!
                                    Profile newProfile = new Profile(username, password, name, following, followers);
                                    addProfileToList(newProfile);

                                    Log.d("Profile:", name);
                                }
                            }else{
                                if (MainUser.getProfile().isFollower(document.get("username").toString())) {
                                    // Get the attributes from each habit in the database
                                    String name = document.get("name").toString();
                                    String username = document.getData().get("username").toString();
                                    String password = "*********";
                                    String following = document.get("following").toString();
                                    String followers = document.get("followers").toString();


                                    // Create new habit and add to the list!
                                    Profile newProfile = new Profile(username, password, name, following, followers);
                                    addProfileToList(newProfile);

                                    Log.d("Profile:", name);
                                }
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

    //opens user profile
    private void goToUser(Profile user){
        Intent intent = new Intent(this, OtherUserProfile.class);
        intent.putExtra("USER", user);
        intent.putExtra("PARENT", mode);

        startActivity(intent);
    }
}