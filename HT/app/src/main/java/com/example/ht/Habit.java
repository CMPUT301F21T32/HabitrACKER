package com.example.ht;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Habit implements Serializable {

    private String name;
    private String description;
    private List<Boolean> selectedDays;
    private int hour;
    private int minute;
    private String date;
    private String username;
    private String habitID;

    public Habit(String name, String description, List<Boolean> selectedDays, int hour, int minute, String date, String username, String habitID) {
        this.name = name;
        this.description = description;
        this.selectedDays = selectedDays;
        this.hour = hour;
        this.minute = minute;
        this.date = date;
        this.username = username;
        this.habitID = habitID;
    }

    public Habit(String name, String description, String selectedDays, String hour, String minute, String date, String username, String habitID) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.username = username;
        this.hour = Integer.parseInt(hour);
        this.minute = Integer.parseInt((minute));
        selectedDays = selectedDays.substring(1, selectedDays.length()-1);
        List<String> daysString = new ArrayList<String>(Arrays.asList(selectedDays.split(",")));
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

    public String getDate() { return date;}

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

    public void setDate(String date) { this.date = date; }

    public void setUsername(String username) { this.username = username; }

}