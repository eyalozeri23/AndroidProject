package com.eyal.eyalo.birthdayremainderapp;

import android.app.DatePickerDialog;
import android.content.Context;

/**
 * Created by eyalo on 2/9/2017.
 */

public class DatePickerFragment extends DatePickerDialog {

    public DatePickerFragment(Context context, OnDateSetListener listener, int year, int month, int dayOfMonth) {
        super(context, listener, year, month, dayOfMonth);
    }
}


