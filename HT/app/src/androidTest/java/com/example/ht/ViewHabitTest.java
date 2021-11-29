package com.example.ht;

import static org.junit.Assert.*;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ViewHabitTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<ViewHabitActivity> rule =
            new ActivityTestRule<ViewHabitActivity>(ViewHabitActivity.class, true, true) {

                @Override
                protected Intent getActivityIntent() {
                    Intent intent = new Intent();
                    Date d = new Date();
                    intent.putExtra("habit", new Habit("test", "test","[true, true, true, true, true, true, true, true]", d.toString(), "test", "true", "test"));

                    return intent;
                }
            };

    /**
     * Runs before all tests and creates solo instance.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference ref = db.collection("users").document("cole");
        ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("sample: ", "Document exists!");
                        // GO TO NEXT ACTIVITY
                        String usernameText = "cole";
                        String password = "123456788";
                        String name = document.get("name").toString();
                        String following = document.get("following").toString();
                        String followers = document.get("followers").toString();

                        Profile temp = new Profile(usernameText, password, name, following, followers);

                        MainUser.setProfile(temp);

                    }
                }
            }
        });
    }
    /**
     * Gets the Activity
     * @throws Exception
     */
    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();
    }

    /**
     * Habit can be viewed correctly and all the correct info shows up
     */
    @Test
    public void testCorrectView() throws InterruptedException {
        solo.assertCurrentActivity("Wrong Activity", ViewHabitActivity.class);
        assertTrue(solo.waitForText("M", 1, 5000));
        assertTrue(solo.waitForText("test", 1, 5000));
        assertTrue(solo.waitForText("Add Event", 1, 5000));
        assertTrue(solo.waitForText("Edit Habit", 1, 5000));




    }


    /**
     * Closes the activity after each test
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();

    }
}

