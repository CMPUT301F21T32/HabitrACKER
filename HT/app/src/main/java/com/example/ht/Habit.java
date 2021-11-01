package com.example.ht;

import java.util.Date;
import java.util.List;

public class Habit {

    private String name;
    private String description;
    private List<Boolean> selectedDays;
    private int hour;
    private int minute;
    private String date;

    public Habit(String name, String desc, List<Boolean> days, int hour, int minute, String date) {
        this.name = name;
        this.description = desc;
        this.selectedDays = days;
        this.hour = hour;
        this.minute = minute;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Boolean> getSelectedDays() {
        return selectedDays;
    }

    public int getHour() { return hour; }

    public int getMinute() { return minute; }

    public String getDate() { return date;}

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    public void setSelectedDays(List<Boolean> days) {
        this.selectedDays = days;
    }

    public void setHour(int hour) { this.hour = hour; }

    public void setMinute(int minute) { this.minute = minute; }

    public void setDate(String date) { this.date = date; }

}
