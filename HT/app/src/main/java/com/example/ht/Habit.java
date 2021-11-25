package com.example.ht;

import android.annotation.SuppressLint;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Habit implements Serializable {

    private String name;
    private String description;
    private List<Boolean> selectedDays;
    private int hour;
    private int minute;
    private Date date;
    private String username;
    private String habitID;

    public Habit(String name, String description, List<Boolean> selectedDays, int hour, int minute, Date date, String username, String habitID) {
        this.name = name;
        this.description = description;
        this.selectedDays = selectedDays;
        this.hour = hour;
        this.minute = minute;
        this.date = date;
        this.username = username;
        this.habitID = habitID;
    }

    @SuppressLint("SimpleDateFormat")
    public Habit(String name, String description, String selectedDays, String hour, String minute, String date, String username, String habitID) {
        this.name = name;
        this.description = description;
        this.username = username;
        this.hour = Integer.parseInt(hour);
        this.minute = Integer.parseInt((minute));
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

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Boolean> getSelectedDays() {
        return this.selectedDays;
    }

    public int getHour() { return hour; }

    public int getMinute() { return minute; }

    public Date getDate() { return date;}

    public String getDateString() { return date.toString(); }

    public String getUsername() { return username; }

    public String getHabitID() {
        return habitID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSelectedDays(List<Boolean> selectedDays) {
        this.selectedDays = selectedDays;
    }

    public void setHour(int hour) { this.hour = hour; }

    public void setMinute(int minute) { this.minute = minute; }

    public void setDate(Date date) { this.date = date; }

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