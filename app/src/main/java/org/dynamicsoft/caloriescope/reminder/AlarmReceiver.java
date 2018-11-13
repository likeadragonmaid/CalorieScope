package org.dynamicsoft.caloriescope.reminder;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import org.dynamicsoft.caloriescope.R;
import org.dynamicsoft.caloriescope.activities.RemindersActivity;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Alarm alarm = new Alarm(context);

        alarm.fromIntent(intent);

        String id = "main_channel";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            CharSequence name = "Channel Name";
            String description = "Channel Description";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(id, name, importance);
            notificationChannel.setDescription(description);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.WHITE);
            notificationChannel.enableVibration(false);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, id);
            notificationBuilder.setSmallIcon(R.drawable.ic_reminder_add);
            notificationBuilder.setContentTitle(alarm.getTitle());
            notificationBuilder.setLights(Color.WHITE, 500, 5000);
            notificationBuilder.setColor(Color.RED);
            notificationBuilder.setDefaults(Notification.DEFAULT_SOUND);
            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
            notificationManagerCompat.notify(1000, notificationBuilder.build());

        } else {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
            Intent repeating_intent = new Intent(context, RemindersActivity.class);
            repeating_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(context,
                    100, repeating_intent, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "1")
                    .setContentIntent(pendingIntent)
                    .setContentTitle(alarm.getTitle())
                    .setContentText("Reminder Text")
                    .setAutoCancel(true)
                    .setSmallIcon(R.drawable.ic_reminder_add);
            notificationManager.notify(100, builder.build());
        }
    }
}
