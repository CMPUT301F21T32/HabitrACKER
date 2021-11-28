package com.example.ht;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class PastEventList extends ArrayAdapter<HabitEvent> {
    private ConstraintLayout layout;
    private ArrayList<HabitEvent> eventList;
    private Context context;
    private int content;

    public PastEventList(@NonNull Context context, ArrayList eventList) {
        super(context, 0, eventList);
        this.eventList= eventList;
        this.context= context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View view= convertView;
        if (view==null){
            view= LayoutInflater.from(context).inflate(R.layout.event_cell, parent, false);
        }
        HabitEvent event= eventList.get(position);

        TextView eventTitle= view.findViewById(R.id.habit_event);
        TextView eventDescription= view.findViewById(R.id.habit_description);
        TextView eventUser= view.findViewById(R.id.username);

        eventTitle.setText(event.getName());
        eventDescription.setText(event.getDescription());
        eventUser.setText(event.getComment());

        return view;
    }

}
