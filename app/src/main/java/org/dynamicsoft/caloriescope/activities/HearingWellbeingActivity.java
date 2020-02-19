/********************************************************************************************************************
 * org/dynamicsoft/caloriescope/activities/HearingWellbeingActivity.java: HearingWellbeing activity for CalorieScope
 ********************************************************************************************************************
 * Copyright (C) 2020 Shouko Komi
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
 ********************************************************************************************************************/

package org.dynamicsoft.caloriescope.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.karlotoy.perfectune.instance.PerfectTune;

import org.dynamicsoft.caloriescope.R;

import static org.dynamicsoft.caloriescope.activities.MainActivity.i1;
import static org.dynamicsoft.caloriescope.activities.MainActivity.i2;
import static org.dynamicsoft.caloriescope.activities.MainActivity.i3;
import static org.dynamicsoft.caloriescope.activities.MainActivity.i4;
import static org.dynamicsoft.caloriescope.activities.MainActivity.i5;
import static org.dynamicsoft.caloriescope.activities.MainActivity.i6;
import static org.dynamicsoft.caloriescope.activities.MainActivity.i8;

public class HearingWellbeingActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SensorEventListener {

    private PerfectTune perfectTune;
    private Boolean isPlaying = false;
    private Button btn_yes, btn_no, btn_next;
    private TextView steps, num, freq, tip;
    private int numeral = 1;
    private int frequency = 2000;
    private AlertDialog.Builder alertDialogBuilder, alertDialogBuilder2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hearing_wellbeing_with_drawer);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //To set Person's name in Nav Drawer
        SharedPreferences pref = getApplicationContext().getSharedPreferences("AppData", 0);
        TextView NavDrawerUserString = navigationView.getHeaderView(0).findViewById(R.id.NavDrawerUserString);
        NavDrawerUserString.setText(pref.getString("UserName", "Welcome"));

        //Handling heart rate activities visibility, this chunk of code must exist in each activity!
        SensorManager mSensorManager;
        Menu nav_Menu = navigationView.getMenu();
        mSensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE) != null) {
            nav_Menu.findItem(R.id.nav_heart_rate).setVisible(true);
        } else {
            nav_Menu.findItem(R.id.nav_heart_rate_camera).setVisible(true);
        }

        perfectTune = new PerfectTune();

        btn_yes = findViewById(R.id.btn_yes);
        btn_no = findViewById(R.id.btn_no);
        btn_next = findViewById(R.id.btn_next);
        steps = findViewById(R.id.steps);
        num = findViewById(R.id.numeral);
        freq = findViewById(R.id.freq);
        tip = findViewById(R.id.tip);

        btn_yes.setVisibility(View.INVISIBLE);
        btn_no.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
            HearingWellbeingActivity.this.moveTaskToBack(true);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_home) {
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            super.onBackPressed();
            //startActivity(i0);finish();
        } else if (id == R.id.nav_news) {
            startActivity(i1);
            finish();
        } else if (id == R.id.nav_about) {
            startActivity(i2);
            finish();
        } else if (id == R.id.nav_settings) {
            startActivity(i3);
            finish();
        } else if (id == R.id.nav_calculator) {
            startActivity(i4);
            finish();
        } else if (id == R.id.nav_heart_rate) {
            startActivity(i5);
            finish();
        } else if (id == R.id.nav_heart_rate_camera) {
            startActivity(i6);
            finish();
        } else if (id == R.id.nav_hearing_wellbeing) {
        } else if (id == R.id.nav_videos) {
            startActivity(i8);
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void next(View view) {
        btn_next.setVisibility(View.INVISIBLE);
        btn_yes.setVisibility(View.VISIBLE);
        btn_no.setVisibility(View.VISIBLE);

        if (frequency == 2000) {
            if (isPlaying) {
                perfectTune.stopTune();
            }
            isPlaying = true;
            perfectTune.setTuneFreq(2000);
            perfectTune.playTune();
        }

        if (frequency == 4000) {
            if (isPlaying) {
                perfectTune.stopTune();
            }
            isPlaying = true;
            perfectTune.setTuneFreq(4000);
            perfectTune.playTune();
        }

        if (frequency == 6000) {
            if (isPlaying) {
                perfectTune.stopTune();
            }
            isPlaying = true;
            perfectTune.setTuneFreq(6000);
            perfectTune.playTune();
        }

        if (frequency == 8000) {
            if (isPlaying) {
                perfectTune.stopTune();
            }
            isPlaying = true;
            perfectTune.setTuneFreq(8000);
            perfectTune.playTune();
        }

        if (frequency == 10000) {
            if (isPlaying) {
                perfectTune.stopTune();
            }
            isPlaying = true;
            perfectTune.setTuneFreq(10000);
            perfectTune.playTune();
        }

        if (frequency == 12000) {
            if (isPlaying) {
                perfectTune.stopTune();
            }
            isPlaying = true;
            perfectTune.setTuneFreq(12000);
            perfectTune.playTune();
        }

        if (frequency == 14000) {
            if (isPlaying) {
                perfectTune.stopTune();
            }
            isPlaying = true;
            perfectTune.setTuneFreq(14000);
            perfectTune.playTune();
        }

        if (frequency == 16000) {
            if (isPlaying) {
                perfectTune.stopTune();
            }
            isPlaying = true;
            perfectTune.setTuneFreq(16000);
            perfectTune.playTune();
        }

        if (frequency == 18000) {
            if (isPlaying) {
                perfectTune.stopTune();
            }
            isPlaying = true;
            perfectTune.setTuneFreq(18000);
            perfectTune.playTune();
        }

        if (frequency == 20000) {
            if (isPlaying) {
                perfectTune.stopTune();
            }
            isPlaying = true;
            perfectTune.setTuneFreq(20000);
            perfectTune.playTune();
        }

        if (frequency == 22000) {
            if (isPlaying) {
                perfectTune.stopTune();
            }
            isPlaying = true;
            perfectTune.setTuneFreq(22000);
            perfectTune.playTune();
        }

        if (frequency == 24000) {
            if (isPlaying) {
                perfectTune.stopTune();
            }
            isPlaying = true;
            perfectTune.setTuneFreq(24000);
            perfectTune.playTune();
        }
    }

    @SuppressLint("SetTextI18n")
    public void yes(View view) {
        if (tip.getVisibility() == View.VISIBLE) {
            tip.setVisibility(View.INVISIBLE);
        }
        perfectTune.stopTune();
        numeral = numeral + 1;
        num.setText(" " + numeral + " ");
        frequency += 2000;
        freq.setText(" " + frequency + " ");

        btn_yes.setVisibility(View.INVISIBLE);
        btn_no.setVisibility(View.INVISIBLE);
        btn_next.setVisibility(View.VISIBLE);

        if (frequency == 24000 && numeral == 12) {
            btn_next.setVisibility(View.INVISIBLE);
            alertDialogBuilder = new AlertDialog.Builder(HearingWellbeingActivity.this);
            alertDialogBuilder.setMessage("Test indicates that you have excellent hearing capability")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            HearingWellbeingActivity.this.finish();
                        }
                    });
            alertDialogBuilder.create();
            alertDialogBuilder.show();
        }
    }

    public void no(View view) {
        btn_yes.setVisibility(View.INVISIBLE);
        btn_no.setVisibility(View.INVISIBLE);
        btn_next.setVisibility(View.INVISIBLE);
        perfectTune.stopTune();

        alertDialogBuilder2 = new AlertDialog.Builder(HearingWellbeingActivity.this);

        if (frequency == 2000) {
            alertDialogBuilder2
                    .setMessage("Test indicates that you have the audition of a person having age more than 90 years")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            HearingWellbeingActivity.this.finish();
                        }
                    });
        }

        if (frequency == 4000) {
            alertDialogBuilder2
                    .setMessage("Test indicates that you have the audition of a person having age between 81 years and 90 years")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            HearingWellbeingActivity.this.finish();
                        }
                    });
        }

        if (frequency == 6000) {
            alertDialogBuilder2
                    .setMessage("Test indicates that you have the audition of a person having age between 76 years and 80 years")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            HearingWellbeingActivity.this.finish();
                        }
                    });
        }

        if (frequency == 8000) {
            alertDialogBuilder2
                    .setMessage("Test indicates that you have the audition of a person having age between 71 years and 75 years")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            HearingWellbeingActivity.this.finish();
                        }
                    });
        }

        if (frequency == 10000) {
            alertDialogBuilder2
                    .setMessage("Test indicates that you have the audition of a person having age between 66 years and 70 years")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            HearingWellbeingActivity.this.finish();
                        }
                    });
        }

        if (frequency == 12000) {
            alertDialogBuilder2
                    .setMessage("Test indicates that you have the audition of a person having age between 61 years and 65 years")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            HearingWellbeingActivity.this.finish();
                        }
                    });
        }

        if (frequency == 14000) {
            alertDialogBuilder2
                    .setMessage("Test indicates that you have the audition of a person having age between 56 years and 60 years")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            HearingWellbeingActivity.this.finish();
                        }
                    });
        }

        if (frequency == 16000) {
            alertDialogBuilder2
                    .setMessage("Test indicates that you have the audition of a person having age between 50 years and 55 years")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            HearingWellbeingActivity.this.finish();
                        }
                    });
        }

        if (frequency == 18000) {
            alertDialogBuilder2
                    .setMessage("Test indicates that you have the audition of a person having age between 40 years and 49 years")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            HearingWellbeingActivity.this.finish();
                        }
                    });
        }

        if (frequency == 20000) {
            alertDialogBuilder2
                    .setMessage("Test indicates that you have the audition of a person having age between 30 years and 39 years")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            HearingWellbeingActivity.this.finish();
                        }
                    });
        }

        if (frequency == 22000) {
            alertDialogBuilder2
                    .setMessage("Test indicates that you have the audition of a person having age between 20 years and 29 years")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            HearingWellbeingActivity.this.finish();
                        }
                    });
        }

        if (frequency == 24000) {
            alertDialogBuilder2
                    .setMessage("Test indicates that you have the audition of an infant")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            HearingWellbeingActivity.this.finish();
                        }
                    });
        }

        alertDialogBuilder2.create();
        alertDialogBuilder2.show();
    }
}