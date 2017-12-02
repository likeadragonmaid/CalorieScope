package org.karan.healthometerx;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by karan on 2/12/17.
 */

public class BackgroundService extends Service
{
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //empty to let sensormanager do it's work
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
