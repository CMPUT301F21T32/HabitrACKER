package com.example.ht;

import static org.junit.Assert.*;

import android.app.Activity;
import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class CreateAccountActivityTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<CreateAccountActivity> rule =
            new ActivityTestRule<>(CreateAccountActivity.class, true, true);

    /**
     * Runs before all tests and creates solo instance.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
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
     * test if errors appear when incorrect data is entered
     */
    @Test
    public void testErrors(){
        solo.assertCurrentActivity("Wrong Activity", CreateAccountActivity.class);

        //test name error and password error
        solo.hideSoftKeyboard();
        solo.clickOnButton("Create");
        assertTrue(solo.waitForText("name cannot be empty", 1, 3000));
        assertTrue(solo.waitForText("Password must be 8 - 21 characters", 1, 3000));

        //test username error
        solo.enterText((EditText) solo.getView(R.id.username_editText), "cole");
        solo.hideSoftKeyboard();
        solo.clickOnButton("Create");
        assertTrue(solo.waitForText("Username is already taken", 1, 10000));

        //test confirm password error
        solo.enterText((EditText) solo.getView(R.id.password_editText), "123456789");
        solo.enterText((EditText) solo.getView(R.id.confirmPassword_editText), "123456788");
        solo.hideSoftKeyboard();
        solo.clickOnButton("Create");
        assertTrue(solo.waitForText("Passwords do not match", 1, 10000));

    }

    /**
     *  Test if sucessfully creates account if info is correct
     */
    @Test
    public void testCreate(){
        solo.assertCurrentActivity("Wrong Activity", CreateAccountActivity.class);

        solo.enterText((EditText) solo.getView(R.id.name_editText), "cole");
        solo.enterText((EditText) solo.getView(R.id.username_editText), "testtest");
        solo.enterText((EditText) solo.getView(R.id.password_editText), "123456789");
        solo.enterText((EditText) solo.getView(R.id.confirmPassword_editText), "123456789");
        solo.hideSoftKeyboard();
        solo.clickOnButton("Create");

        assertTrue(solo.waitForText("friends", 1, 10000));



    }

    /**
     * Closes the activity after each test
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference ref = db.collection("Habits").document("testtest");

        ref.delete();

    }
}
