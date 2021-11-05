package com.example.ht;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class ProfileFeedTest {
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

    @Test
    public void addActivity() throws Exception {
        solo.clickOnButton("Add Habit");
        solo.assertCurrentActivity("Wrong Activity", AddActivity.class);
        solo.enterText((EditText) solo.getView(R.id.editText_name), "New Title");
        solo.enterText((EditText) solo.getView(R.id.editText_desc), "New Description");
        solo.clickOnButton("FINISH");
    }

    @Test
    public void editHabit() throws Exception {
        addActivity();
        solo.clickInList(1);
        solo.clickOnButton("Edit Habit");
        solo.assertCurrentActivity("Wrong Activity", ViewHabitActivity.class);
        solo.enterText((EditText) solo.getView(R.id.editText_desc), "I changed the description");
    }

    @Test
    public void deleteHabit() throws Exception {
        addActivity();
        solo.clickOnButton("Delete Habit");
        solo.clickInList(0);
    }
}
