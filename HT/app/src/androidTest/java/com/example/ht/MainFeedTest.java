package com.example.ht;
import static org.junit.Assert.assertFalse;

import static org.junit.Assert.assertTrue;

import android.app.Activity;
import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class MainFeedTest {


        private Solo solo;

        @Rule
        public ActivityTestRule<LoginActivity> rule =
                new ActivityTestRule<>(LoginActivity.class, true, true);

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
         * Checks if user anahitatest2 can see the habits of another user anahitatest
         * on main feed without following them
         * anahitatest has two habits - one is private and one is public
         * anahitatest2 has one public habit and is not following or followed by anyone
         *
         */
        @Test
        public void testMainNoFollow(){
            solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);

            solo.enterText((EditText) solo.getView(R.id.loginName_editText), "anahitatest2");
            solo.enterText((EditText) solo.getView(R.id.loginPassword_editText), "anahitatest2");
            solo.hideSoftKeyboard();
            solo.clickOnButton("Log In");

            assertFalse(solo.waitForText("Private", 1, 2000));
            assertFalse(solo.waitForText("Public", 1, 2000));
        }

    /**
     * Checks if user anahitatest3 can see the public habits of another user anahitatest
     * on main feed with following them, but cannot see the private habits of anahitatest
     * anahitatest has two habits - one is private and one is public
     * anahitatest3 has no habits and is not followed by anyone, is following anahitatest
     */
    @Test
    public void testMainFollow(){
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);

        solo.enterText((EditText) solo.getView(R.id.loginName_editText), "anahitatest3");
        solo.enterText((EditText) solo.getView(R.id.loginPassword_editText), "anahitatest3");
        solo.hideSoftKeyboard();
        solo.clickOnButton("Log In");

        assertTrue(solo.waitForText("Public", 1, 2000));
        assertFalse(solo.waitForText("Private", 1, 2000));
    }

    }
