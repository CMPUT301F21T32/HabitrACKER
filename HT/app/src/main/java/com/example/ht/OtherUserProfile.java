// CODE REPURPOSED FROM SELFPROFILE
package com.example.ht;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ht.AddActivity;
import com.example.ht.CustomList;
import com.example.ht.Habit;
import com.example.ht.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class OtherUserProfile extends AppCompatActivity {
    final int NOT_FOLLOWING = 0;
    final int FOLLOWING = 1;
    final int REQUESTED = 2;

    String parent;

    Intent intent;
    TextView usernameLabel;
    TextView nameLabel;
    ListView mainList;
    Button request;
    ImageButton back;
    ArrayList<Habit> habitList = new ArrayList<>();
    ArrayAdapter<Habit> habitAdapter;
    boolean isDeleting = false;
    String username;
    Profile otherUser;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    int followState;
    // Declarations for the xml list, list that contains habit items, and the adapter for the array

    /**
     * This starts the activity, and inflates the self_profile
     * layout. NOTE: in AndroidManifest.xml, I set this screen
     * to be the main (first) screen, to change this,
     * take out the <activity></activity> with .SelfProfile
     * OUTSIDE of the <intent-filter></intent-filter>
     * you also might have to change the android:parent tag
     * on some of the other activities.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_user_profile);

        intent = getIntent();
        otherUser = (Profile) intent.getSerializableExtra("USER");
        username = otherUser.getUsername();

        parent = intent.getStringExtra("PARENT");


        populateList();

        Button deleteButton = findViewById(R.id.delete_button);
        usernameLabel = findViewById(R.id.other_username);
        nameLabel = findViewById(R.id.other_full_name);


        // set Username and name to that of current user


        usernameLabel.setText("@" + username);
        nameLabel.setText(otherUser.getName());


        request = findViewById(R.id.requestButton);
        if(otherUser.getUsername().equals(MainUser.getProfile().getUsername())){
            // if this is the main users profile
            request.setVisibility(View.GONE);

        } else if(MainUser.getProfile().isFollowing(username)){
            // main user is already following this user

            request.setText("Unfollow");
            request.setBackgroundColor(getResources().getColor(R.color.green));
            followState = FOLLOWING;


        }else {

            DocumentReference ref = db.collection("Follow Requests")
                                      .document(MainUser.getProfile().getUsername() + "" +username);
            ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {

                            // if main user has already requested to follow this user

                            Log.d("sample: ", "Document exists!");

                            request.setText("Cancel Request");
                            request.setBackgroundColor(getResources().getColor(R.color.grey));
                            followState = REQUESTED;

                        } else {

                            request.setText("Request Follow");
                            request.setBackgroundColor(getResources().getColor(R.color.blue));
                            followState = NOT_FOLLOWING;

                        }
                    }
                }
            });

        }

        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(followState == NOT_FOLLOWING){
                    request.setText("Cancel Request");
                    request.setBackgroundColor(getResources().getColor(R.color.grey));
                    followState = REQUESTED;
                    HashMap<String, String> req = new HashMap<>();
                    req.put("to", username);
                    req.put("from", MainUser.getProfile().getUsername());


                    // add a new follow request
                    db.collection("Follow Requests")
                            .document(MainUser.getProfile().getUsername() + "" +username)
                            .set(req)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // These are a method which gets executed when the task is succeeded
                                    Log.d("REQUEST: ", "Data has been added successfully!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // These are a method which gets executed if there’s any problem
                                    Log.d("REQUEST: ", "Data could not be added!" + e.toString());
                                }
                            });

                }else if(followState == FOLLOWING){
                    request.setText("Request Follow");
                    request.setBackgroundColor(getResources().getColor(R.color.blue));
                    otherUser.removeFollower(MainUser.getProfile().getUsername());
                    MainUser.getProfile().removerFollowing(otherUser.getUsername());
                    followState = NOT_FOLLOWING;
                }else{
                    request.setText("Request Follow");
                    request.setBackgroundColor(getResources().getColor(R.color.blue));
                    followState = NOT_FOLLOWING;

                    db.collection("Follow Requests")
                            .document(MainUser.getProfile().getUsername() + "" +username)
                            .delete();
                }
            }
        });


        back = findViewById(R.id.other_back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(parent.equals("following")){
                    viewFollowing();
                }else if (parent.equals("followers")){
                    viewFollowers();
                }else{
                    goToSearch();
                }
            }
        });


        // Setting up list item click
        mainList = findViewById(R.id.other_list);
        mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * This function sets an onItemClickAdapter for the listview
             * NOTE: THIS ONLY WORKS WHEN THE LINERAR LAYOUT OF THE CELL
             * CONTENT HAS android:descendantFocusability = "blocksDescendants"
             * After the button (FOLLOW REQUESTS FOR NOW) is clicked, you
             * are in deletion mode. You can now click on any list item to
             * delete it. After it is deleted from the database, it is removed
             * from the list, and we update the adapter accordingly.
             * This is all inside a condition
             * @param adapterView
             * @param view
             * @param i
             * @param l
             */
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                viewHabit(habitList.get(i), username);

            }
        });



    }

    /**
     * This function takes a habit, and adds it
     * to the list, updating the adapter.
     * Unfortunately I could not just add the item in
     * populateList() because it wont work.
     * @param habit
     */
    public void addHabitToList(Habit habit) {
        habitList.add(habit);
        Log.d("LIST CHECK", habitList.get(0).getName());

        mainList = findViewById(R.id.other_list);
        //xml list reference

        habitAdapter = new CustomList(this, habitList);
        mainList.setAdapter(habitAdapter);
        habitAdapter.notifyDataSetChanged();
        // update list
    }

    /**
     * This function looks inside the database,
     * and gets the title, description, hour,
     * minute, date, selected days, and username
     * from the habit in the database, and
     * stores those values into a habit object
     * which then adds to the habitList
     */
    public void populateList() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Habits")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && MainUser.getProfile().isFollowing(username)) {
                        habitList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            if(document.get("username") != null &&
                                    document.get("username").toString().equals(otherUser.getUsername())) {
                                // Get the attributes from each habit in the database
                                String title = document.getData().get("name").toString();
                                String description = document.getData().get("description").toString();
                                String date = document.getData().get("date").toString();
                                String selectedDays = document.getData().get("selectedDays").toString();
                                String username = document.getData().get("username").toString();
                                String id = document.getId();

                                // Create new habit and add to the list!
                                Habit newHabit = new Habit(title, description, selectedDays, date, username, id);
                                addHabitToList(newHabit);

                                Log.d("HABIT:", title);
                            }
                        }
                    } else {
                        Log.d("ERROR:", "Error getting documents: ", task.getException());
                    }
                });


    }


    /**
     * Overrides onResume to update the list
     * everytime the page is resumed.
     */
    @Override public void onResume() {
        super.onResume();
        populateList();
    }


    // starts view follow activity for following
    private void viewFollowing(){
        Intent intent = new Intent(this, ViewFollowActivity.class);
        intent.putExtra("MODE", "following");
        startActivity(intent);
        finish();
    }


    // starts view follow activity for followers
    private void viewFollowers(){
        Intent intent = new Intent(this, ViewFollowActivity.class);
        intent.putExtra("MODE", "followers");
        startActivity(intent);
        finish();
    }

    //starts the profile activity
    private void goToSearch(){
        Intent intent = new Intent(this, Search.class);
        startActivity(intent);
        finish();

    }

    // starts ViewHabit activity for habit
    private void viewHabit(Habit habit, String un){
        Intent intent = new Intent(this, ViewHabitActivity.class);
        intent.putExtra("habit", habit);


        startActivity(intent);
        finish();
    }
}