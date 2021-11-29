package com.example.ht;



import static org.junit.Assert.*;

import android.app.Activity;
import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;


/**
 * Test class for MainActivity. All the UI tests are written here. Robotium test framework is
 used
 */
public class LoginActivityTest {
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
     * Checks if error occurs if login is wrong
     */
    @Test
    public void testError(){
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);

        solo.enterText((EditText) solo.getView(R.id.loginName_editText), "cole");
        solo.enterText((EditText) solo.getView(R.id.loginPassword_editText), "cole");
        solo.hideSoftKeyboard();
        solo.clickOnButton("Log In");

        assertTrue(solo.waitForText("Username or Password is invalid", 1, 5000));
    }

    /**
     * tests if logged in when password and user name are correct
     */
    @Test
    public void testLogin(){
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        solo.enterText((EditText) solo.getView(R.id.loginName_editText), "cole");
        solo.enterText((EditText) solo.getView(R.id.loginPassword_editText), "123456788");
        solo.hideSoftKeyboard();
        solo.clickOnButton("Log In");
        assertTrue(solo.waitForText("Hunt", 1, 10000));
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
