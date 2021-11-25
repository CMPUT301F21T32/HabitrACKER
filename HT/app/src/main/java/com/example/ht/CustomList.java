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

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import org.xmlpull.v1.XmlPullParser;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class CustomList extends ArrayAdapter<Habit> {
    private ConstraintLayout layout;
    private ArrayList<Habit> habitList;
    private Context context;
    private int content;

    PieChart pieChart;
    ArrayList<PieEntry> pieEntryList = new ArrayList<>();

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

        TextView consistencyTitle = view.findViewById(R.id.consistency_text);
        consistencyTitle.setText(String.format("\nConsistency:\n %d/%d",0,habit.getTimesPassed()));
//        pieChart = view.findViewById(R.id.pie_chart);
//        pieChart.setUsePercentValues(true);
//        pieEntryList.add(new PieEntry(10,"Completed"));
//        pieEntryList.add(new PieEntry(10,"a"));
//        pieEntryList.add(new PieEntry(10,"b"));
//        pieEntryList.add(new PieEntry(10,"c"));
//        PieDataSet pieDataSet = new PieDataSet(pieEntryList,"Consistency");
//        PieData pieData = new PieData(pieDataSet);
//        pieChart.setData(pieData);

        return view;
    }
}

