package com.example.ht;

import android.app.Activity;
import android.content.Intent;
import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

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
        solo.enterText((EditText) solo.getView(R.id.editText_name), String.format("hello"));
        solo.clickOnView(solo.getView(R.id.finishAddActivityButton));
        solo.assertCurrentActivity("Wrong Activity", SelfProfile.class);

        solo.clickOnButton(3);
        solo.clickInList(0);



    }
}