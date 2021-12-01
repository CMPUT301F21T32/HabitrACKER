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

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Allows the event list to be displayed in the view
 *
 * @aurhor Jacqueline
 */

public class PastEventList extends ArrayAdapter<HabitEvent>{
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
            view= LayoutInflater.from(context).inflate(R.layout.cell_content, parent, false);
        }
        HabitEvent event= eventList.get(position);

        TextView eventTitle= view.findViewById(R.id.habit_title);
        TextView eventDescription= view.findViewById(R.id.habit_description);
        TextView eventUser= view.findViewById(R.id.username);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String getName= db.collection("HabitEvents")
                .get(Source.valueOf("name"))
                .toString();

        String getComment= db.collection("HabitEvents")
                .get(Source.valueOf("comment"))
                .toString();

        eventTitle.setText(getName);
        eventDescription.setText(getComment);
        eventUser.setText("defaultUser");

        return view;
    }

}

