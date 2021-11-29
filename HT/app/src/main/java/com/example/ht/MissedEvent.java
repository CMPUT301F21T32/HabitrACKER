package com.example.ht;

public class MissedEvent extends HabitEvent{
    public MissedEvent(String habitID, String name, String description) {
        super(habitID, name, description);
    }
    // alerts user when they miss an event
}
