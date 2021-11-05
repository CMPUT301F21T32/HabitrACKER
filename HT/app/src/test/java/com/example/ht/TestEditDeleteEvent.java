package com.example.ht;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TestEditDeleteEvent {
    //Create HabitEvent objects that will be tested
    // Note: when editing Event, in the AddEvent, the description is the comment
    @Before
    public HabitEvent passedTest(){
        return new HabitEvent("habitID", "description that's also comments must be under 20, is not", "hour", "minute", "name", "username");
    }
    @Before
    public HabitEvent failedTest(){

        return new HabitEvent("habitID", "is under 20", "hour", "minute", "name", "username");
    }

    // Tests for HabitEvent Objects
    // Check if there are 20 characters in comment
    @Test
    public void testComments1(){
        // Should pass
        int commentLength= passedTest().description.length();
        assertEquals(commentLength, commentLength<20);
    }
    @Test
    public void testComments2(){
        //Should fail
        int commentLength= failedTest().description.length();
        assertEquals(commentLength, commentLength>20);
    }


}
