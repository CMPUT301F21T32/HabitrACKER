package com.example.ht;

import static org.junit.Assert.assertTrue;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

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

public class SearchTest {
    private Solo solo;
    private Profile testUser;

    @Rule
    public ActivityTestRule<Search> rule =
            new ActivityTestRule<>(Search.class, true, true);

    /**
     * Runs before all tests and creates solo instance and sets up main user
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
     * Checks if error occurs if login is wrong
     */
    @Test
    public void testSearchBar(){
        solo.assertCurrentActivity("Wrong Activity", Search.class);
        solo.waitForText("Search", 1, 5000);

        solo.enterText((EditText) solo.getView(R.id.searchTextBox), "cole");
        solo.hideSoftKeyboard();
        solo.clickOnView((ImageButton) solo.getView(R.id.searchGoButton));

        assertTrue(solo.waitForText("cole12", 1, 5000));
    }

    /**
     * tests if logged in when password and user name are correct
     */
    @Test
    public void testOtherUser(){

        solo.assertCurrentActivity("Wrong Activity", Search.class);

        solo.enterText((EditText) solo.getView(R.id.searchTextBox), "cole");
        solo.hideSoftKeyboard();
        solo.clickOnView((ImageButton) solo.getView(R.id.searchGoButton));

        solo.waitForText("cole12", 1, 5000);

        solo.clickOnText("cole12");

        assertTrue(solo.waitForText("Request Follow", 1, 5000));

        solo.clickOnButton("Request Follow");

        assertTrue(solo.waitForText("Cancel Request", 1, 5000));

        solo.clickOnText("Cancel Request");

        assertTrue(solo.waitForText("Request Follow", 1, 5000));




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
