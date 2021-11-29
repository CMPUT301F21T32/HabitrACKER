package com.example.ht;

import android.app.Activity;
import android.content.Intent;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FollowRequestTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<ViewHabitActivity> rule =
            new ActivityTestRule<ViewHabitActivity>(ViewHabitActivity.class, true, true) {

                @Override
                protected Intent getActivityIntent() {
                    Intent intent = new Intent();
                    List<Boolean> temp = new ArrayList<Boolean>();
                    Date d = new Date();
                    intent.putExtra("habit", new Habit("test", "test","test", temp.toString(), "4", "4", "test"));
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
     * test if the accept request button works
     */
    @Test
    public void testAcceptButton() throws InterruptedException {
        solo.assertCurrentActivity("Wrong Activity", ViewHabitActivity.class);
        solo.clickOnView(solo.getView(R.id.addEvent_button));
        solo.wait(3000);
        solo.assertCurrentActivity("Wrong Activity", AddEventActivity.class);


    }

    /**
     * test if the deny request button works
     */
    @Test
    public void testDenyButton() throws InterruptedException {
        solo.assertCurrentActivity("Wrong Activity", ViewHabitActivity.class);
        solo.clickOnView(solo.getView(R.id.edit_button));
        solo.wait(3000);
        solo.assertCurrentActivity("Wrong Activity", AddActivity.class);


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
