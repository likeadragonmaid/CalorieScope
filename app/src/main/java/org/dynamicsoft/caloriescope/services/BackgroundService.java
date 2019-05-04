/****************************************************************************************************************
 * org/dynamicsoft/caloriescope/services/BackgroundService.java: BackgroundService java source for CalorieScope
 ****************************************************************************************************************
 * Copyright (C) 2019 Shouko Komi
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
 ****************************************************************************************************************/

package org.dynamicsoft.caloriescope.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class BackgroundService extends Service { //This file is a placeholder and will be worked upon in the future
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //left empty to let sensormanager do it's work
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
