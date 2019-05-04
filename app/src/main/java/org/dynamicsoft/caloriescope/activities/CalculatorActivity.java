/*********************************************************************************************************
 * org/dynamicsoft/caloriescope/activities/CalculatorActivity.java: Calculator activity for CalorieScope
 *********************************************************************************************************
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
 *********************************************************************************************************/

package org.dynamicsoft.caloriescope.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.dynamicsoft.caloriescope.R;

import static org.dynamicsoft.caloriescope.activities.MainActivity.LastBMI;
import static org.dynamicsoft.caloriescope.activities.MainActivity.LastWHR;
import static org.dynamicsoft.caloriescope.activities.MainActivity.i1;
import static org.dynamicsoft.caloriescope.activities.MainActivity.i2;
import static org.dynamicsoft.caloriescope.activities.MainActivity.i3;
import static org.dynamicsoft.caloriescope.activities.MainActivity.i5;
import static org.dynamicsoft.caloriescope.activities.MainActivity.i6;
import static org.dynamicsoft.caloriescope.activities.MainActivity.i7;
import static org.dynamicsoft.caloriescope.activities.MainActivity.i8;

public class CalculatorActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SensorEventListener {

    public SharedPreferences pref;
    private TextView result_textview, calculate_title, calculate_firstheading,
            calculate_secondheading, calculate_thridheading,
            calculate_firstheading_under1, calculate_firstheading_under2, calculate_firstheading_under3,
            calculate_secondheading_under1, calculate_secondheading_under2, calculate_secondheading_under3,
            calculate_thridheading_under1, calculate_thridheading_under2, calculate_thridheading_under3,
            bmi_last;
    private float weight, height, waist, hip, result;
    private EditText weight_waist, height_hip;
    private int state = 0;
    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_bmi:
                    state = 0;
                    weight_waist.setHint("Weight (kg)");
                    height_hip.setHint("Height (m)");
                    weight_waist.setText(null);
                    height_hip.setText(null);
                    result_textview.setText(null);

                    calculate_title.setText(null);
                    calculate_firstheading.setText(null);
                    calculate_secondheading.setText(null);
                    calculate_thridheading.setText(null);


                    calculate_firstheading_under1.setText(null);
                    calculate_secondheading_under1.setText(null);
                    calculate_firstheading_under3.setText(null);

                    calculate_secondheading_under2.setText(null);
                    calculate_secondheading_under3.setText(null);
                    calculate_firstheading_under2.setText(null);

                    calculate_thridheading_under1.setText(null);
                    calculate_thridheading_under2.setText(null);
                    calculate_thridheading_under3.setText(null);


                    bmi_last.setText(null);


                    return true;
                case R.id.navigation_whr:
                    state = 1;
                    weight_waist.setHint("Waist (inch)");
                    height_hip.setHint("Hip (inch)");
                    weight_waist.setText(null);
                    height_hip.setText(null);
                    result_textview.setText(null);

                    calculate_title.setText(null);
                    calculate_firstheading.setText(null);
                    calculate_secondheading.setText(null);
                    calculate_thridheading.setText(null);


                    calculate_firstheading_under1.setText(null);
                    calculate_secondheading_under1.setText(null);
                    calculate_firstheading_under3.setText(null);

                    calculate_secondheading_under2.setText(null);
                    calculate_secondheading_under3.setText(null);
                    calculate_firstheading_under2.setText(null);

                    calculate_thridheading_under1.setText(null);
                    calculate_thridheading_under2.setText(null);
                    calculate_thridheading_under3.setText(null);


                    bmi_last.setText(null);

                    return true;
            }
            return false;
        }
    };
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator_with_drawer);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("AppData", 0);
        editor = pref.edit();

        result_textview = findViewById(R.id.result_textview);
        weight_waist = findViewById(R.id.weight_waist);
        height_hip = findViewById(R.id.height_hip);
        Button calculate = findViewById(R.id.calculate);

        calculate_title = findViewById(R.id.calculate_title);
        calculate_firstheading = findViewById(R.id.calculate_firstheading);
        calculate_secondheading = findViewById(R.id.calculate_secondheading);
        calculate_thridheading = findViewById(R.id.calculate_thirdheading);

        calculate_firstheading_under1 = findViewById(R.id.calculate_firstheading_under1);
        calculate_firstheading_under2 = findViewById(R.id.calculate_firstheading_under2);
        calculate_firstheading_under3 = findViewById(R.id.calculate_firstheading_under3);

        calculate_secondheading_under1 = findViewById(R.id.calculate_second_heading_under_1);
        calculate_secondheading_under2 = findViewById(R.id.calculate_second_heading_under_2);
        calculate_secondheading_under3 = findViewById(R.id.calculate_secondheading_under3);

        calculate_thridheading_under1 = findViewById(R.id.calculate_thirdheading_under1);
        calculate_thridheading_under2 = findViewById(R.id.calculate_third_heading_under_2);
        calculate_thridheading_under3 = findViewById(R.id.calculate_thirdheading_under3);

        bmi_last = findViewById(R.id.bmi_last);


        calculate.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View arg0) {
                if (state == 0
                        && !weight_waist.getText().toString().equals("")
                        && weight_waist.getText().toString().length() != 0
                        && !height_hip.getText().toString().equals("")
                        && height_hip.getText().toString().length() != 0) {
                    weight = Float.valueOf(weight_waist.getText().toString());
                    height = Float.valueOf(height_hip.getText().toString());
                    result = weight / (height * height);
                    result_textview.setText("BMI: " + String.valueOf(result));
                    editor.putString("LastBMI", String.valueOf(result));
                    editor.apply();
                    LastBMI.setText("Your Body Mass index is " + String.valueOf(result));
                    LastBMI.setVisibility(View.VISIBLE);

                    calculate_title.setText("Body Mass Index");
                    calculate_firstheading.setText("BMI");
                    calculate_secondheading.setText(null);
                    calculate_thridheading.setText("Weight Status");


                    calculate_firstheading_under1.setText("Below 18.5");
                    calculate_secondheading_under1.setText("18.5-24.9");
                    calculate_firstheading_under3.setText("25.0-29.9");

                    calculate_secondheading_under2.setText(null);
                    calculate_secondheading_under3.setText(null);
                    calculate_firstheading_under2.setText(null);

                    calculate_thridheading_under1.setText("Underweight");
                    calculate_thridheading_under2.setText("Normal");
                    calculate_thridheading_under3.setText("OverWeight");

                    bmi_last.setText("30.0 and above Obese");
                }
                if (state == 1
                        && !weight_waist.getText().toString().equals("")
                        && weight_waist.getText().toString().length() != 0
                        && !height_hip.getText().toString().equals("")
                        && height_hip.getText().toString().length() != 0) {
                    waist = Float.valueOf(weight_waist.getText().toString());
                    hip = Float.valueOf(height_hip.getText().toString());
                    result = waist / hip;
                    result_textview.setText("WHR: " + String.valueOf(result));
                    editor.putString("LastWHR", String.valueOf(result));
                    editor.apply();
                    LastWHR.setText("Your Waist Hip ratio is " + String.valueOf(result));
                    LastWHR.setVisibility(View.VISIBLE);

                    calculate_title.setText("Waist To Hip Ratio");
                    calculate_firstheading.setText("Health Risk");
                    calculate_secondheading.setText("Women");
                    calculate_thridheading.setText("Men");

                    calculate_firstheading_under1.setText("Low");
                    calculate_firstheading_under2.setText("0.80 or lower");
                    calculate_firstheading_under3.setText("High");

                    calculate_secondheading_under1.setText("Moderate");
                    calculate_secondheading_under2.setText("0.81-0.85");
                    calculate_secondheading_under3.setText("0.96 or 1.0");

                    calculate_thridheading_under1.setText("0.95 or lower");
                    calculate_thridheading_under2.setText("0.86 or higher");
                    calculate_thridheading_under3.setText("1.0 or higher");

                    bmi_last.setText(null);
                }
            }
        });

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

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
            CalculatorActivity.this.moveTaskToBack(true);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
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

        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}