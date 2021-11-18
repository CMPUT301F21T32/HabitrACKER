package com.example.ht;



import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.ht.Habit;
import com.example.ht.R;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;

public class ProfileList extends ArrayAdapter<Profile> {
    private ConstraintLayout layout;
    private ArrayList<Profile> profileList;
    private Context context;
    private int content;

    public ProfileList(Context context, ArrayList habitList) {
        super(context, 0, habitList);
        this.profileList = habitList;
        this.context = context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.profile_cell, parent,false);
        }

        Profile profile = profileList.get(position);

        TextView name = view.findViewById(R.id.profileCell_name);
        TextView username = view.findViewById(R.id.profileCell_username);

        name.setText(profile.getName());
        username.setText("@"+ profile.getUsername());
        return view;
    }
}