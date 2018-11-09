package org.dynamicsoft.caloriescope.reminder;

import android.content.Context;
import android.content.Intent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class Alarm implements Comparable<Alarm> {
    public static final int ONCE = 0;
    public static final int WEEKLY = 2;
    public static final int HOURLY = 1;
    public static final int MONTHLY = 3;
    public static final int YEARLY = 4;
    public static final int NEVER = 0;
    public static final int EVERY_DAY = 0x7f;
    private Context mContext;
    private long mId;
    private String mTitle;
    private long mDate;
    private int mOccurence;
    private int mDays;
    private long mNextOccurence;

    public Alarm(Context context) {
        mContext = context;
        mId = 0;
        mTitle = "";
        mDate = System.currentTimeMillis();
        mOccurence = ONCE;
        mDays = EVERY_DAY;
        update();
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public int getOccurence() {
        return mOccurence;
    }

    public void setOccurence(int occurence) {
        mOccurence = occurence;
        update();
    }

    public long getDate() {
        return mDate;
    }

    public void setDate(long date) {
        mDate = date;
        update();
    }


    public int getDays() {
        return mDays;
    }

    public void setDays(int days) {
        mDays = days;
        update();
    }

    public long getNextOccurence() {
        return mNextOccurence;
    }

    public boolean getOutdated() {
        return mNextOccurence < System.currentTimeMillis();
    }

    public int compareTo(Alarm aThat) {
        final long thisNext = getNextOccurence();
        final long thatNext = aThat.getNextOccurence();
        final int BEFORE = -1;
        final int EQUAL = 0;
        final int AFTER = 1;

        if (this == aThat)
            return EQUAL;

        if (thisNext > thatNext)
            return AFTER;
        else if (thisNext < thatNext)
            return BEFORE;
        else
            return EQUAL;
    }

    public void update() {
        Calendar now = Calendar.getInstance();

        if (mOccurence == WEEKLY) {
            Calendar alarm = Calendar.getInstance();

            alarm.setTimeInMillis(mDate);
            alarm.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));

            if (mDays != NEVER) {
                while (true) {
                    int day = (alarm.get(Calendar.DAY_OF_WEEK) + 5) % 7;

                    if (alarm.getTimeInMillis() > now.getTimeInMillis() && (mDays & (1 << day)) > 0)
                        break;

                    alarm.add(Calendar.DAY_OF_MONTH, 1);
                }
            } else {
                alarm.add(Calendar.YEAR, 10);
            }

            mNextOccurence = alarm.getTimeInMillis();
        } else {
            mNextOccurence = mDate;
        }

        mDate = mNextOccurence;
    }

    public void toIntent(Intent intent) {
        intent.putExtra("id", mId);
        intent.putExtra("title", mTitle);
        intent.putExtra("ic_reminder_date", mDate);
        intent.putExtra("occurence", mOccurence);
        intent.putExtra("days", mDays);
    }

    public void fromIntent(Intent intent) {
        mId = intent.getLongExtra("id", 0);
        mTitle = intent.getStringExtra("title");
        mDate = intent.getLongExtra("ic_reminder_date", 0);
        mOccurence = intent.getIntExtra("occurence", 0);
        mDays = intent.getIntExtra("days", 0);
        update();
    }

    public void serialize(DataOutputStream dos) throws IOException {
        dos.writeLong(mId);
        dos.writeUTF(mTitle);
        dos.writeLong(mDate);
        dos.writeInt(mOccurence);
        dos.writeInt(mDays);
    }

    public void deserialize(DataInputStream dis) throws IOException {
        mId = dis.readLong();
        mTitle = dis.readUTF();
        mDate = dis.readLong();
        mOccurence = dis.readInt();
        mDays = dis.readInt();
        update();
    }
}