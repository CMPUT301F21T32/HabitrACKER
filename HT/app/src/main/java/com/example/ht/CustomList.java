package com.example.ht;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import org.xmlpull.v1.XmlPullParser;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class CustomList extends ArrayAdapter<Habit> {
    private ConstraintLayout layout;
    private ArrayList<Habit> habitList;
    private Context context;
    private int content;

    public CustomList(Context context, ArrayList habitList) {
        super(context, 0, habitList);
        this.habitList = habitList;
        this.context = context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.cell_content, parent,false);
        }

        Habit habit = habitList.get(position);

        TextView habitTitle = view.findViewById(R.id.habit_title);
        TextView habitDescription = view.findViewById(R.id.habit_description);
        TextView username = view.findViewById(R.id.username);
        habitTitle.setText(habit.getName());
        habitDescription.setText(habit.getDescription());
        username.setText(habit.getUsername());

        ProgressBar progressBar = view.findViewById(R.id.progress);
        progressBar.setProgress((habit.getTimesCompleted()/habit.getTimesPassed())*100);

        return view;
    }
}

