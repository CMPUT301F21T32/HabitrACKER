package com.example.ht;

import android.content.Context;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

public class HabitEventList extends ArrayList<HabitEvent> {
    private ConstraintLayout layout;
    private ArrayList<HabitEvent> EventList;
    private Context context;

    public HabitEventList (ArrayList habitList, Context context){
        //super(context, 0, habitList);
        this.EventList= habitList;
        this.context= context;
    }

    



}
