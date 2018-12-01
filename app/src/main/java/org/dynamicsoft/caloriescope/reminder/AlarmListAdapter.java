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

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.dynamicsoft.caloriescope.R;

public class AlarmListAdapter extends BaseAdapter {
    private final String TAG = "AlarmMe";
    private final Context mContext;
    private final LayoutInflater mInflater;
    private final DateTime mDateTime;
    private final int mColorOutdated;
    private final int mColorActive;
    private final AlarmManager mAlarmManager;

    public AlarmListAdapter(Context context) {
        mContext = context;
        DataSource mDataSource = DataSource.getInstance(context);

        mInflater = LayoutInflater.from(context);
        mDateTime = new DateTime(context);

        mColorOutdated = mContext.getResources().getColor(R.color.alarm_title_outdated);
        mColorActive = mContext.getResources().getColor(R.color.white);
        mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        dataSetChanged();
    }

    public void save() {
        DataSource.save();
    }

    public void update(Alarm alarm) {
        DataSource.update(alarm);
        dataSetChanged();
    }

    public void updateAlarms() {
        Log.i(TAG, "AlarmListAdapter.updateAlarms()");
        for (int i = 0; i < DataSource.size(); i++)
            DataSource.update(DataSource.get(i));
        dataSetChanged();
    }

    public void add(Alarm alarm) {
        DataSource.add(alarm);
        dataSetChanged();
    }

    public void delete(int index) {
        cancelAlarm(DataSource.get(index));
        DataSource.remove(index);
        dataSetChanged();
    }

    public void onSettingsUpdated() {
        mDateTime.update();
        dataSetChanged();
    }

    public int getCount() {
        return DataSource.size();
    }

    public Alarm getItem(int position) {
        return DataSource.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"InflateParams", "SetTextI18n"})
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        Alarm alarm = DataSource.get(position);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.reminders_list_item, null);
            holder = new ViewHolder();
            holder.title = convertView.findViewById(R.id.item_title);
            holder.details = convertView.findViewById(R.id.item_details);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.title.setText(alarm.getTitle());
        holder.details.setText(mDateTime.formatDetails(alarm));

        if (alarm.getOutdated()) {
            holder.title.setTextColor(mColorOutdated);
            holder.details.setText(mDateTime.formatDetails(alarm) + " Expired");
        } else
            holder.title.setTextColor(mColorActive);

        return convertView;
    }

    private void dataSetChanged() {
        for (int i = 0; i < DataSource.size(); i++)
            setAlarm(DataSource.get(i));
        notifyDataSetChanged();
    }

    private void setAlarm(Alarm alarm) {
        PendingIntent sender;
        Intent intent;

        if (!alarm.getOutdated()) {
            intent = new Intent(mContext, AlarmReceiver.class);
            alarm.toIntent(intent);
            sender = PendingIntent.getBroadcast(mContext, (int) alarm.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
            mAlarmManager.set(AlarmManager.RTC_WAKEUP, alarm.getDate(), sender);
        }
        Log.i(TAG, "AlarmListAdapter.setAlarm(" + alarm.getId() + ", '" + alarm.getTitle() + "', " + alarm.getDate() + ")");
    }

    private void cancelAlarm(Alarm alarm) {
        PendingIntent sender;
        Intent intent;

        intent = new Intent(mContext, AlarmReceiver.class);
        sender = PendingIntent.getBroadcast(mContext, (int) alarm.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mAlarmManager.cancel(sender);
    }

    static class ViewHolder {
        TextView title;
        TextView details;
    }
}