/****************************************************************************************************
 * org/dynamicsoft/caloriescope/activities/SettingsActivity.java: Settings activity for CalorieScope
 ****************************************************************************************************
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
 ****************************************************************************************************/

package org.dynamicsoft.caloriescope.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import org.dynamicsoft.caloriescope.R;

import static org.dynamicsoft.caloriescope.Utils.DefaultNewsCountryForNewsAPI;
import static org.dynamicsoft.caloriescope.Utils.NewsAPIOrgKey;
import static org.dynamicsoft.caloriescope.Utils.YTAPIKey;
import static org.dynamicsoft.caloriescope.Utils.YTExerciseChannelID;
import static org.dynamicsoft.caloriescope.Utils.YTHealthyFoodChannelID;
import static org.dynamicsoft.caloriescope.activities.MainActivity.i1;
import static org.dynamicsoft.caloriescope.activities.MainActivity.i2;
import static org.dynamicsoft.caloriescope.activities.MainActivity.i4;
import static org.dynamicsoft.caloriescope.activities.MainActivity.i5;
import static org.dynamicsoft.caloriescope.activities.MainActivity.i6;
import static org.dynamicsoft.caloriescope.activities.MainActivity.i7;
import static org.dynamicsoft.caloriescope.activities.MainActivity.i8;

public class SettingsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SensorEventListener {

    private TextView SensitivityTextView;
    private int progressChangedValue = 0;
    private float CurrentSensitivityValue;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private EditText YouTubeKeyEditText, YouTubeHealthyFoodChannelIDText, YouTubeExerciseChannelIDText, NewsApiOrgKeyEditText, DefaultNewsCountryEditText;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_with_drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SeekBar sensitivitySeekBar = findViewById(R.id.SensitivitySeekBar);
        SensitivityTextView = findViewById(R.id.SensitivityTextView);
        Button saveSettings = findViewById(R.id.SaveSettings);
        Button loadDefaults = findViewById(R.id.LoadDefaults);
        Button clearAppData = findViewById(R.id.ClearAppData);
        YouTubeKeyEditText = findViewById(R.id.YouTubeKeyEditText);
        YouTubeHealthyFoodChannelIDText = findViewById(R.id.YouTubeHealthyFoodChannelIDText);
        YouTubeExerciseChannelIDText = findViewById(R.id.YouTubeExerciseChannelIDText);
        NewsApiOrgKeyEditText = findViewById(R.id.NewsApiOrgKeyEditText);
        DefaultNewsCountryEditText = findViewById(R.id.DefaultNewsCountryEditText);

        pref = getApplicationContext().getSharedPreferences("AppData", 0);
        editor = pref.edit();
        CurrentSensitivityValue = pref.getFloat("CurrentSensitivityValue", 30f);
        SensitivityTextView.setText("Accelerometer sensitivity: " + (int) CurrentSensitivityValue);
        DefaultNewsCountryEditText.setText(pref.getString("DefaultNewsCountryEditText", DefaultNewsCountryForNewsAPI));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            sensitivitySeekBar.setMax(100);
            sensitivitySeekBar.setMin(1);
        } else {
            sensitivitySeekBar.setMax(100);
        }

        sensitivitySeekBar.setProgress((int) CurrentSensitivityValue);

        sensitivitySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @SuppressLint("SetTextI18n")
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
                SensitivityTextView.setText("Accelerometer sensitivity: " + progressChangedValue);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @SuppressLint("SetTextI18n")
            public void onStopTrackingTouch(SeekBar seekBar) {
                SensitivityTextView.setText("Accelerometer sensitivity: " + progressChangedValue);
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        saveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                CurrentSensitivityValue = (float) progressChangedValue;
                editor.putFloat("CurrentSensitivityValue", CurrentSensitivityValue);
                if (!DefaultNewsCountryEditText.getText().toString().equals("") && !DefaultNewsCountryEditText.getText().toString().equals(null)) {
                    editor.putString("DefaultNewsCountryEditText", DefaultNewsCountryEditText.getText().toString());
                }
                if (!YouTubeKeyEditText.getText().toString().equals("") && !YouTubeKeyEditText.getText().toString().equals(null)) {
                    editor.putString("YouTubeKeyEditText", YouTubeKeyEditText.getText().toString());
                }
                if (!YouTubeHealthyFoodChannelIDText.getText().toString().equals("") && !YouTubeHealthyFoodChannelIDText.getText().toString().equals(null)) {
                    editor.putString("YouTubeHealthyFoodChannelIDText", YouTubeHealthyFoodChannelIDText.getText().toString());
                }
                if (!YouTubeExerciseChannelIDText.getText().toString().equals("") && !YouTubeExerciseChannelIDText.getText().toString().equals(null)) {
                    editor.putString("YouTubeExerciseChannelIDText", YouTubeExerciseChannelIDText.getText().toString());
                }
                if (!NewsApiOrgKeyEditText.getText().toString().equals("") && !NewsApiOrgKeyEditText.getText().toString().equals(null)) {
                    editor.putString("NewsApiOrgKeyEditText", NewsApiOrgKeyEditText.getText().toString());
                }

                editor.apply();
                Toast.makeText(getApplicationContext(), "Saved successfully!", Toast.LENGTH_LONG).show();
                Intent i = getBaseContext().getPackageManager()
                        .getLaunchIntentForPackage(getBaseContext().getPackageName());
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        loadDefaults.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View arg0) {
                SensitivityTextView.setText("Sensitivity: 30");
                CurrentSensitivityValue = 30f;
                editor.putFloat("CurrentSensitivityValue", CurrentSensitivityValue);
                editor.putString("DefaultNewsCountryEditText", DefaultNewsCountryForNewsAPI);
                editor.putString("YouTubeKeyEditText", YTAPIKey);
                editor.putString("YouTubeHealthyFoodChannelIDText", YTHealthyFoodChannelID);
                editor.putString("YouTubeExerciseChannelIDText", YTExerciseChannelID);
                editor.putString("NewsApiOrgKeyEditText", NewsAPIOrgKey);
                editor.apply();
                Toast.makeText(getApplicationContext(), "Defaults settings loaded sucessfully!", Toast.LENGTH_LONG).show();
                Intent i = getBaseContext().getPackageManager()
                        .getLaunchIntentForPackage(getBaseContext().getPackageName());
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        clearAppData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SettingsActivity.this);
                alertDialog.setTitle("WARNING!");
                alertDialog.setCancelable(false);
                alertDialog.setMessage("Are you sure, you want to clear app data?\nThis will not clear your name and gender.");
                alertDialog.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                editor.putInt("waterGlasses", 0);
                                editor.putInt("currentWaterQuantity", 0);
                                editor.putInt("caffeineCups", 0);
                                editor.putInt("currentCaffeineQuantity", 0);
                                editor.putLong("numSteps", 0);
                                editor.putFloat("Calories", 0);
                                editor.putString("LastBPM", "");
                                editor.putString("LastBMI", "");
                                editor.putString("LastWHR", "");
                                editor.putFloat("CurrentSensitivityValue", 30f);
                                editor.putString("DefaultNewsCountryEditText", DefaultNewsCountryForNewsAPI);
                                editor.putString("YouTubeKeyEditText", YTAPIKey);
                                editor.putString("YouTubeHealthyFoodChannelIDText", YTHealthyFoodChannelID);
                                editor.putString("YouTubeExerciseChannelIDText", YTExerciseChannelID);
                                editor.putString("NewsApiOrgKeyEditText", NewsAPIOrgKey);
                                editor.apply();
                                Toast.makeText(getApplicationContext(), "App data cleared sucessfully!", Toast.LENGTH_LONG).show();
                                Intent i = getBaseContext().getPackageManager()
                                        .getLaunchIntentForPackage(getBaseContext().getPackageName());
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                            }
                        });
                alertDialog.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                alertDialog.show();
            }
        });

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

        if (mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null) {
            sensitivitySeekBar.setVisibility(View.GONE);
            SensitivityTextView.setVisibility(View.GONE);
        } else {
            sensitivitySeekBar.setVisibility(View.VISIBLE);
        }

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
            SettingsActivity.this.moveTaskToBack(true);
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
        } else if (id == R.id.nav_calculator) {
            startActivity(i4);
            finish();
        } else if (id == R.id.nav_heart_rate) {
            startActivity(i5);
            finish();
        } else if (id == R.id.nav_heart_rate) {
            startActivity(i5);
            finish();
        } else if (id == R.id.nav_heart_rate_camera) {
            startActivity(i6);
            finish();
        } else if (id == R.id.nav_hearing_wellbeing) {
            startActivity(i7);
            finish();
        } else if (id == R.id.nav_videos) {
            startActivity(i8);
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        finish();
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
}