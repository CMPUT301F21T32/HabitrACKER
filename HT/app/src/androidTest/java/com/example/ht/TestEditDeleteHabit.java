package com.example.ht;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.app.Activity;
import android.content.Intent;
import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.robotium.solo.Solo;

public class TestEditDeleteHabit {

    private Solo solo;

    // Derived from https://stackoverflow.com/a/45502924
    @Rule
    public ActivityTestRule<SelfProfile> rule =
            new ActivityTestRule<SelfProfile>(SelfProfile.class, true, true) {

                @Override
                protected Intent getActivityIntent() {
                    Intent intent = new Intent();
                    intent.putExtra("USERNAME", "Test");
                    return intent;
                }
            };

    /**
     * Runs before all tests and creates solo instance.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }

    /**
     * Gets the Activity
     * @throws Exception
     */
    @Test
    public void start() throws Exception {
        Activity activity = rule.getActivity();
    }

    /**
     * test if the delete habit button works
     */
    @Test
    public void testDeleteButton() throws InterruptedException {


        solo.clickOnButton(4);
        solo.enterText((EditText) solo.getView(R.id.editText_name), String.format("delete1"));
        solo.clickOnView(solo.getView(R.id.finishAddActivityButton));
        solo.assertCurrentActivity("Wrong Activity", SelfProfile.class);

        solo.clickOnButton(3);
        assertTrue(solo.waitForText("delete1", 1, 2000));
        solo.clickInList(0);
        assertFalse(solo.waitForText("delete1", 1, 2000));

    }
    /**
     * test if the edit habit button works
     */
    @Test
    public void testEditButton() throws InterruptedException {
        solo.clickOnButton(4);
        solo.enterText((EditText) solo.getView(R.id.editText_name), String.format("editoriginal1"));
        solo.clickOnView(solo.getView(R.id.finishAddActivityButton));
        solo.clickInList(0);
        solo.clickOnButton(0);
        solo.clearEditText((EditText) solo.getView(R.id.editText_name));
        solo.enterText((EditText) solo.getView(R.id.editText_name), String.format("editnew1"));
        solo.clickOnView(solo.getView(R.id.finishAddActivityButton));
        assertTrue(solo.waitForText("editnew1", 1, 2000));


    }



}