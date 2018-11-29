package org.dynamicsoft.caloriescope.reminder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

class DateTime {
    private final Context mContext;
    private String[] mFullDayNames;
    private String[] mShortDayNames;
    private boolean mWeekStartsOnMonday;
    private boolean m24hClock;
    private SimpleDateFormat mTimeFormat;
    private SimpleDateFormat mDateFormat;

    DateTime(Context context) {
        mContext = context;
        update();
    }

    @SuppressLint("SimpleDateFormat")
    void update() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        mWeekStartsOnMonday = prefs.getBoolean("week_starts_pref", false);
        m24hClock = prefs.getBoolean("use_24h_pref", false);

        mDateFormat = new SimpleDateFormat("E MMM d, yyyy");

        if (m24hClock)
            mTimeFormat = new SimpleDateFormat("H:mm");
        else
            mTimeFormat = new SimpleDateFormat("h:mm a");

        mFullDayNames = new String[7];
        mShortDayNames = new String[7];

        SimpleDateFormat fullFormat = new SimpleDateFormat("EEEE");
        SimpleDateFormat shortFormat = new SimpleDateFormat("E");
        Calendar calendar;

        if (mWeekStartsOnMonday)
            calendar = new GregorianCalendar(2012, Calendar.AUGUST, 6);
        else
            calendar = new GregorianCalendar(2012, Calendar.AUGUST, 5);

        for (int i = 0; i < 7; i++) {
            mFullDayNames[i] = fullFormat.format(calendar.getTime());
            mShortDayNames[i] = shortFormat.format(calendar.getTime());
            calendar.add(Calendar.DAY_OF_WEEK, 1);
        }
    }

    boolean is24hClock() {
        return m24hClock;
    }

    String formatTime(Alarm alarm) {
        return mTimeFormat.format(new Date(alarm.getDate()));
    }

    String formatDate(Alarm alarm) {
        return mDateFormat.format(new Date(alarm.getDate()));
    }

    String formatDays(Alarm alarm) {
        boolean[] days = getDays(alarm);
        StringBuilder res = new StringBuilder();

        if (alarm.getDays() == Alarm.NEVER)
            res = new StringBuilder("Never");
        else if (alarm.getDays() == Alarm.EVERY_DAY)
            res = new StringBuilder("Every day");
        else {
            for (int i = 0; i < 7; i++)
                if (days[i])
                    res.append(("" == res.toString()) ? mShortDayNames[i] : ", " + mShortDayNames[i]);
        }

        return res.toString();
    }

    String formatDetails(Alarm alarm) {
        String res = "???";

        if (alarm.getOccurrence() == Alarm.ONCE)
            res = formatDate(alarm);
        else if (alarm.getOccurrence() == Alarm.WEEKLY)
            res = formatDays(alarm);

        res += ", " + formatTime(alarm);

        return res;
    }

    boolean[] getDays(Alarm alarm) {
        int offs = mWeekStartsOnMonday ? 0 : 1;
        boolean[] rDays = new boolean[7];
        int aDays = alarm.getDays();

        for (int i = 0; i < 7; i++)
            rDays[(i + offs) % 7] = (aDays & (1 << i)) > 0;

        return rDays;
    }

    void setDays(Alarm alarm, boolean[] days) {
        int offs = mWeekStartsOnMonday ? 0 : 1;
        int sDays = 0;

        for (int i = 0; i < 7; i++)
            sDays |= days[(i + offs) % 7] ? (1 << i) : (0);

        alarm.setDays(sDays);
    }

    String[] getFullDayNames() {
        return mFullDayNames;
    }

    public String[] getShortDayNames() {
        return mShortDayNames;
    }
}