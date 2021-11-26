package com.example.ht;

//import android.annotation.SuppressLint;

import android.annotation.SuppressLint;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Hunter
 * This class represents a user's habits
 *
 */
public class Habit implements Serializable {

    private String name;
    private String description;
    private List<Boolean> selectedDays;
    private Date date;
    private String username;
    private final String habitID;

    public Habit(String name, String description, List<Boolean> selectedDays, Date date, String username, String habitID) {
        this.name = name;
        this.description = description;
        this.selectedDays = selectedDays;
        this.date = date;
        this.username = username;
        this.habitID = habitID;
    }

    /**
     * A constructor which parses variables that are saved as strings
     */
    @SuppressLint("SimpleDateFormat")
    public Habit(String name, String description, String selectedDays, String date, String username, String habitID) {
        this.name = name;
        this.description = description;
        this.username = username;
        // Try-catch necessary for the simpleDateFormat to accept data
        try {
            this.date = new SimpleDateFormat("yyyy/MM/dd").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // Convert the selectedDays string into a list
        selectedDays = selectedDays.substring(1, selectedDays.length()-1);
        List<String> daysString = new ArrayList<String>(Arrays.asList(selectedDays.split(", ")));
        this.selectedDays = new ArrayList<Boolean>();
        for (String s : daysString) {
            this.selectedDays.add(Boolean.parseBoolean(s));
        }
        this.habitID = habitID;
    }

    /**
     * Gets the name of a habit
     *
     * @return The habit's name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the description of a habit
     *
     * @return The habit's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the list of booleans representing whether a habit occurs on a given day
     * of the week (begins at sunday and ends at saturday)
     *
     * @return The habit's selected days
     */
    public List<Boolean> getSelectedDays() {
        return this.selectedDays;
    }

    /**
     * Gets the day that the habit begins
     *
     * @return The habit's start date
     */
    public Date getDate() { return date;}

    /**
     * Gets the day that the habit begins formatted as a string
     *
     * @return The habit's start date in string format
     */
    public String getDateString() { return date.toString(); }

    /**
     * Gets the username of the person who the habit belongs to
     *
     * @return The username of the habit creator
     */
    public String getUsername() { return username; }

    /**
     * Gets the unique id that represents the habits id
     *
     * @return The habit's id
     */
    public String getHabitID() {
        return habitID;
    }

    /**
     * Sets the name of a habit
     *
     * @param name The habit's new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the description of a habit
     *
     * @param description The habit's new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the list of booleans representing whether a habit occurs on a given day
     * of the week (begins at sunday and ends at saturday)
     *
     * @param selectedDays The habit's new list of selected days
     */
    public void setSelectedDays(List<Boolean> selectedDays) {
        this.selectedDays = selectedDays;
    }

    /**
     * Sets the day that the habit begins
     *
     * @param date The habit's new start date
     */
    public void setDate(Date date) { this.date = date; }

    /**
     * Sets the username of the person who the habit belongs to
     *
     * @param username The new username of the habit creator
     */
    public void setUsername(String username) { this.username = username; }

    /**
     * Checks if a habit occurs today by checking if it occurs on the current day of the week and
     * if the start date is not after today.
     * @return true if the habit occurs today, false otherwise
     */
    public boolean isToday() {
        Date today = new Date();
        int dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        dayOfWeek = (dayOfWeek-1)%7; // Set it so sunday is 0
        return (!this.date.after(today) && // Checks that the start date has happened
                this.selectedDays.get(dayOfWeek)); // Checks that the habit occurs today
    }

}