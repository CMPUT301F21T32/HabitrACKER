package com.example.ht;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

/**
 * @author Hunter
 * This is a class for the fragment used for picking a date
 *
 */
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), (AddActivity)getActivity(), year, month, day);
    }

    /**
     * This will be overwritten in the AddAcitivty where habits are created
     * @param view
     * @param year year of the date chosen
     * @param month month of the date chosen
     * @param day day of the date chosen
     */
    public void onDateSet(DatePicker view, int year, int month, int day) {

    }
}