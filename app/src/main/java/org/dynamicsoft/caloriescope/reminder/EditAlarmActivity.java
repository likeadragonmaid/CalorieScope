/*****************************************************************************************************
 * org/dynamicsoft/caloriescope/reminder/EditAlarmActivity.java: EditAlarm activity for CalorieScope
 *****************************************************************************************************
 * Copyright (C) 2018 Sourav Kainth
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 ******************************************************************************************************/

package org.dynamicsoft.caloriescope.reminder;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import org.dynamicsoft.caloriescope.R;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class EditAlarmActivity extends Activity {
    private static final int DATE_DIALOG_ID = 0;
    private static final int TIME_DIALOG_ID = 1;
    private static final int DAYS_DIALOG_ID = 2;
    private EditText mTitle;
    private Button mDateButton;
    private Button mTimeButton;
    private Alarm mAlarm;
    private DateTime mDateTime;
    private GregorianCalendar mCalendar;
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;
    private final DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;

            mCalendar = new GregorianCalendar(mYear, mMonth, mDay, mHour, mMinute);
            mAlarm.setDate(mCalendar.getTimeInMillis());

            updateButtons();
        }
    };

    private final TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            mHour = hourOfDay;
            mMinute = minute;

            mCalendar = new GregorianCalendar(mYear, mMonth, mDay, mHour, mMinute);
            mAlarm.setDate(mCalendar.getTimeInMillis());

            updateButtons();
        }
    };

    private final TextWatcher mTitleChangedListener = new TextWatcher() {
        public void afterTextChanged(Editable s) {
            mAlarm.setTitle(mTitle.getText().toString());
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
    };

    private final AdapterView.OnItemSelectedListener mOccurrenceSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
            mAlarm.setOccurrence(position);
            updateButtons();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.content_reminders_edit_alarm);

        mTitle = findViewById(R.id.title);
        Spinner mOccurrence = findViewById(R.id.occurrence_spinner);
        mDateButton = findViewById(R.id.date_button);
        mTimeButton = findViewById(R.id.time_button);

        mAlarm = new Alarm(this);
        mAlarm.fromIntent(getIntent());

        mDateTime = new DateTime(this);

        mTitle.setText(mAlarm.getTitle());
        mTitle.addTextChangedListener(mTitleChangedListener);

        mOccurrence.setSelection(mAlarm.getOccurrence());
        mOccurrence.setOnItemSelectedListener(mOccurrenceSelectedListener);

        mCalendar = new GregorianCalendar();
        mCalendar.setTimeInMillis(mAlarm.getDate());
        mYear = mCalendar.get(Calendar.YEAR);
        mMonth = mCalendar.get(Calendar.MONTH);
        mDay = mCalendar.get(Calendar.DAY_OF_MONTH);
        mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
        mMinute = mCalendar.get(Calendar.MINUTE);

        updateButtons();

        Toolbar toolbar = findViewById(R.id.tool_bar);
        toolbar.setTitle("Add Reminder");
        toolbar.setTitleTextColor(Color.WHITE);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (DATE_DIALOG_ID == id)
            return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
        else if (TIME_DIALOG_ID == id)
            return new TimePickerDialog(this, mTimeSetListener, mHour, mMinute, mDateTime.is24hClock());
        else if (DAYS_DIALOG_ID == id)
            return DaysPickerDialog();
        else
            return null;
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        if (DATE_DIALOG_ID == id)
            ((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);
        else if (TIME_DIALOG_ID == id)
            ((TimePickerDialog) dialog).updateTime(mHour, mMinute);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.menu_exit) {
            EditAlarmActivity.this.moveTaskToBack(true);
        }

        return super.onOptionsItemSelected(item);
    }

    public void onDateClick(View view) {
        if (Alarm.ONCE == mAlarm.getOccurrence())
            showDialog(DATE_DIALOG_ID);
        else if (Alarm.WEEKLY == mAlarm.getOccurrence())
            showDialog(DAYS_DIALOG_ID);
    }

    public void onTimeClick(View view) {
        showDialog(TIME_DIALOG_ID);
    }

    public void onDoneClick(View view) {

        if (mTitle.getText().toString().length() != 0 && mTitle.getText().toString() != null && !mTitle.getText().toString().equals("")) {
            Intent intent = new Intent();

            mAlarm.toIntent(intent);
            setResult(RESULT_OK, intent);
            finish();
        } else {
            Toast.makeText(this, "Please Enter Title", Toast.LENGTH_SHORT).show();
            mTitle.requestFocus();
        }
    }

    public void onCancelClick(View view) {
        setResult(RESULT_CANCELED, null);
        finish();
    }

    private void updateButtons() {
        if (Alarm.ONCE == mAlarm.getOccurrence())
            mDateButton.setText(mDateTime.formatDate(mAlarm));
        else if (Alarm.WEEKLY == mAlarm.getOccurrence())
            mDateButton.setText(mDateTime.formatDays(mAlarm));
        mTimeButton.setText(mDateTime.formatTime(mAlarm));
    }

    private Dialog DaysPickerDialog() {
        AlertDialog.Builder builder;
        final boolean[] days = mDateTime.getDays(mAlarm);
        final String[] names = mDateTime.getFullDayNames();

        builder = new AlertDialog.Builder(this);

        builder.setTitle("Week days");

        builder.setMultiChoiceItems(names, days, new DialogInterface.OnMultiChoiceClickListener() {
            public void onClick(DialogInterface dialog, int whichButton, boolean isChecked) {
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                mDateTime.setDays(mAlarm, days);
                updateButtons();
            }
        });

        builder.setNegativeButton("Cancel", null);

        return builder.create();
    }
}