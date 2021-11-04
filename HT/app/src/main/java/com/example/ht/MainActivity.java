package com.example.ht;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView mainList;
    ArrayList<Habit> habitList = new ArrayList<>();
    ArrayAdapter<Habit> habitAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_feed);

//        Habit habit1 = new Habit("Make my bed", "I want to make" +
//                " my bed every morning. It is set for every morning at 9am.");
//        Habit habit2 = new Habit("Wash the dishes", "I want to wash dishes every day after dinner at 7pm.");
//        Habit habit3 = new Habit("Walk the dog", "Every morning at 10am after breakfast I want to walk tha pooch :))))");
//
//        habitList.add(habit1);
//        habitList.add(habit2);
//        habitList.add(habit3);

        mainList = findViewById(R.id.habit_list);

        habitAdapter = new CustomList(this, habitList);
        mainList.setAdapter(habitAdapter);
    }
}