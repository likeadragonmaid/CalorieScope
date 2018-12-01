/**************************************************************************
 *
 * Copyright (C) 2012-2015 Alex Taradov <alex@taradov.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *************************************************************************
 * Modified by Karanvir Singh and Sourav Kainth
 */

package org.dynamicsoft.caloriescope.reminder;

import android.content.Context;
import android.content.Intent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class Alarm implements Comparable<Alarm> {
    public static final int ONCE = 0;
    public static final int WEEKLY = 1;
    public static final int NEVER = 0;
    public static final int EVERY_DAY = 0x7f;
    private long mId;
    private String mTitle;
    private long mDate;
    private int mOccurrence;
    private int mDays;
    private long mNextOccurrence;

    public Alarm(Context context) {
        mId = 0;
        mTitle = "";
        mDate = System.currentTimeMillis();
        mOccurrence = ONCE;
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

    public int getOccurrence() {
        return mOccurrence;
    }

    public void setOccurrence(int occurrence) {
        mOccurrence = occurrence;
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

    private long getNextOccurrence() {
        return mNextOccurrence;
    }

    public boolean getOutdated() {
        return mNextOccurrence < System.currentTimeMillis();
    }

    public int compareTo(Alarm aThat) {
        final long thisNext = getNextOccurrence();
        final long thatNext = aThat.getNextOccurrence();
        final int BEFORE = -1;
        final int EQUAL = 0;
        final int AFTER = 1;
        if (this == aThat)
            return EQUAL;
        return Long.compare(thisNext, thatNext);
    }

    public void update() {
        Calendar now = Calendar.getInstance();

        if (mOccurrence == WEEKLY) {
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

            mNextOccurrence = alarm.getTimeInMillis();
        } else {
            mNextOccurrence = mDate;
        }

        mDate = mNextOccurrence;
    }

    public void toIntent(Intent intent) {
        intent.putExtra("id", mId);
        intent.putExtra("title", mTitle);
        intent.putExtra("ic_reminder_date", mDate);
        intent.putExtra("occurrence", mOccurrence);
        intent.putExtra("days", mDays);
    }

    public void fromIntent(Intent intent) {
        mId = intent.getLongExtra("id", 0);
        mTitle = intent.getStringExtra("title");
        mDate = intent.getLongExtra("ic_reminder_date", 0);
        mOccurrence = intent.getIntExtra("occurrence", 0);
        mDays = intent.getIntExtra("days", 0);
        update();
    }

    public void serialize(DataOutputStream dos) throws IOException {
        dos.writeLong(mId);
        dos.writeUTF(mTitle);
        dos.writeLong(mDate);
        dos.writeInt(mOccurrence);
        dos.writeInt(mDays);
    }

    public void deserialize(DataInputStream dis) throws IOException {
        mId = dis.readLong();
        mTitle = dis.readUTF();
        mDate = dis.readLong();
        mOccurrence = dis.readInt();
        mDays = dis.readInt();
        update();
    }
}