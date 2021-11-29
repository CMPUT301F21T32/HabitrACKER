/**
 * Habit test
 * @author Hunter
 *
 * This contains all tests related to the habit class
 */

package com.example.ht;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class HabitTest {

    // Create a habit with a start date in the future
    public Habit createLaterHabit(){
        return new Habit("Running", "cardio", "[true, false, false, true, false, false, true]", "2050/01/01", "Test", "true", "TestID");
    }

    // Create a habit with a start date today that does not occur today
    public Habit createOtherDayHabit() {
        List<Boolean> days = new ArrayList<>(Collections.nCopies(7, false));
        Date today = new Date();
        return new Habit("Running", "cardio", days.toString(), today.toString(), "Test", "true", "TestID");
    }

    // Create a habit that occurs today
    public Habit createTodayHabit() {
        List<Boolean> days = new ArrayList<>(Collections.nCopies(7, false));
        int dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        days.set((dayOfWeek-1)%7, true);
        Date today = new Date();
        return new Habit("Running", "cardio", days.toString(), today.toString(), "Test","true", "TestID");
    }

    /**
     * Creates the habits and verifies that they satisfy all tests
     */
    @Test
    public void isTodayTest() {
        Habit habit = createLaterHabit();
        assertFalse(habit.isToday());
        habit = createOtherDayHabit();
        assertFalse(habit.isToday());
        habit = createTodayHabit();
        assertTrue(habit.isToday());
    }
}
