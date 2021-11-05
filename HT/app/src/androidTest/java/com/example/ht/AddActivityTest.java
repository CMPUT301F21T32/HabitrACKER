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

public class AddActivityTest {
    private Solo solo;

    // Derived from https://stackoverflow.com/a/45502924
    @Rule
    public ActivityTestRule<AddActivity> rule =
            new ActivityTestRule<AddActivity>(AddActivity.class, true, true) {

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
     * Test character limits on the title
     */
    @Test
    public void titleCharLimitTest() {
        solo.enterText((EditText) solo.getView(R.id.editText_name), String.format("%0" + 21 + "d", 0));
        solo.clickOnView(solo.getView(R.id.finishAddActivityButton));
        solo.assertCurrentActivity("Wrong Activity", AddActivity.class);

        solo.clearEditText((EditText) solo.getView(R.id.editText_name));

        solo.enterText((EditText) solo.getView(R.id.editText_name), String.format("%0" + 20 + "d", 0));
        solo.clickOnView(solo.getView(R.id.finishAddActivityButton));
        solo.assertCurrentActivity("Wrong Activity", SelfProfile.class);
    }

    /**
     * Test character limits on the description
     */
    @Test
    public void descCharLimitTest() {
        solo.enterText((EditText) solo.getView(R.id.editText_name), "descCharLimitTest");

        solo.enterText((EditText) solo.getView(R.id.editText_desc), String.format("%0" + 31 + "d", 0));
        solo.clickOnView(solo.getView(R.id.finishAddActivityButton));
        solo.assertCurrentActivity("Wrong Activity", AddActivity.class);

        solo.clearEditText((EditText) solo.getView(R.id.editText_desc));

        solo.enterText((EditText) solo.getView(R.id.editText_desc), String.format("%0" + 30 + "d", 0));
        solo.clickOnView(solo.getView(R.id.finishAddActivityButton));
        solo.assertCurrentActivity("Wrong Activity", SelfProfile.class);
    }
}