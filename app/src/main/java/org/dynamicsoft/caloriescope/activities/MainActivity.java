package org.dynamicsoft.caloriescope.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.dynamicsoft.caloriescope.R;
import org.dynamicsoft.caloriescope.accelerometerCounter.StepDetector;
import org.dynamicsoft.caloriescope.accelerometerCounter.StepListener;
import org.dynamicsoft.caloriescope.services.BackgroundService;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SensorEventListener, StepListener {

    public static Intent i0, i1, i2, i3, i4, i5, i6, i7, i8, i9, i10;
    public long numSteps;
    public int waterGlasses = 0, caffeineCups = 0, currentWaterQuantity, currentCaffeineQuantity;
    public float Calories, SensorSensitivityTemp;
    public ImageView ClearFluids, addCaffeine, addWater;
    public SharedPreferences pref;
    public SharedPreferences.Editor editor;
    public ProgressBar circularProgressbar;
    private StepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor accel, mSensor;
    private boolean isPedometerSensorPresent = false, HeartRateSensorIsPresent = false;
    private Button BtnStart, BtnStop, BtnReset;
    private TextView TvSteps, CalorieView, currentWaterValue, currentCaffeineValue, waterQuantity, caffeineQuantity, stepsInCircle, TodayDateAndTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calorie_scope_root_nav_drawer);
        i0 = new Intent(this, MainActivity.class);
        i1 = new Intent(this, NewsActivity.class);
        i2 = new Intent(this, AboutActivity.class);
        i3 = new Intent(this, SettingsActivity.class);
        i4 = new Intent(this, CalculatorActivity.class);
        i5 = new Intent(this, HeartRateSensorActivity.class);
        i6 = new Intent(this, HeartRateCameraActivity.class);
        i7 = new Intent(this, HearingWellbeingActivity.class);
        i8 = new Intent(this, VideosActivity.class);
        i9 = new Intent(this, DietManagerActivity.class);
        i10 = new Intent(this, RemindersActivity.class);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("AppData", 0);
        editor = pref.edit();
        SensorSensitivityTemp = pref.getFloat("CurrentSensitivityValue", 30f);

        startService(new Intent(this, BackgroundService.class));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TvSteps = findViewById(R.id.tv_steps);
        CalorieView = findViewById(R.id.CalorieView);
        BtnStart = findViewById(R.id.btn_start);
        BtnStop = findViewById(R.id.btn_stop);
        BtnReset = findViewById(R.id.btn_reset);
        addWater = findViewById(R.id.addWater);
        addCaffeine = findViewById(R.id.addCaffeine);
        currentWaterValue = findViewById(R.id.currentWaterValue);
        currentCaffeineValue = findViewById(R.id.currentCaffeineValue);
        waterQuantity = findViewById(R.id.waterQuantity);
        caffeineQuantity = findViewById(R.id.caffeineQuantity);
        ClearFluids = findViewById(R.id.btnClearFluidIntake);
        stepsInCircle = findViewById(R.id.stepsInCircle);
        circularProgressbar = findViewById(R.id.circularProgressbar);
        TodayDateAndTime = findViewById(R.id.TodayDateAndTime);

        //Set date

        Date full = Calendar.getInstance().getTime();
        TodayDateAndTime.setText(full.toString().substring(0, 10) + ", " + full.toString().substring(30, 34));

        waterGlasses = pref.getInt("waterGlasses", 0);
        currentWaterQuantity = pref.getInt("currentWaterQuantity", 0);
        currentWaterValue.setText("" + waterGlasses);
        waterQuantity.setText("( " + currentWaterQuantity + " ml" + " )");
        caffeineCups = pref.getInt("caffeineCups", 0);
        currentCaffeineQuantity = pref.getInt("currentCaffeineQuantity", 0);
        currentCaffeineValue.setText("" + caffeineCups);
        caffeineQuantity.setText("( " + currentCaffeineQuantity + " mg" + " )");

        numSteps = pref.getLong("numSteps", 0);
        Calories = pref.getFloat("Calories", 0);

        //Set personalization

        TextView NavDrawerUserString = navigationView.getHeaderView(0).findViewById(R.id.NavDrawerUserString);
        NavDrawerUserString.setText(pref.getString("UserName", "Welcome"));

        if (pref.getString("personalInfoSet", "") == ""){

            LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
            View promptView = layoutInflater.inflate(R.layout.personal_info_alert, null);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
            alertDialogBuilder.setView(promptView);

            final EditText PersonName = promptView.findViewById(R.id.PersonName);
            final RadioGroup GenderRadioGroup = promptView.findViewById(R.id.GenderRadioGroup);

            GenderRadioGroup.clearCheck();
            GenderRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @SuppressLint("ResourceType")
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    RadioButton rb = group.findViewById(checkedId);
                    if (null != rb && checkedId > -1) {
                        editor.putString("Gender", rb.getText().toString());
                    }
                }
            });

            alertDialogBuilder.setCancelable(false).setTitle("Welcome").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    editor.putString("UserName", PersonName.getText().toString());
                    editor.putString("personalInfoSet","true");
                    editor.apply();
                }
            });

            AlertDialog SaveUserDetails = alertDialogBuilder.create();
            SaveUserDetails.show();
        }

        // Get an instance of the SensorManager

        sensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null) {
            mSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            isPedometerSensorPresent = true;
        } else {
            isPedometerSensorPresent = false;
            sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
            accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            simpleStepDetector = new StepDetector();
            simpleStepDetector.registerListener(this);
        }

        BtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (isPedometerSensorPresent == false) {
                    sensorManager.registerListener(MainActivity.this, accel, SensorManager.SENSOR_DELAY_FASTEST);
                } else {
                    sensorManager.registerListener(MainActivity.this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
                }

                BtnStart.setVisibility(View.INVISIBLE);
                BtnReset.setVisibility(View.INVISIBLE);
                BtnStop.setVisibility(View.VISIBLE);
            }
        });

        BtnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                TvSteps.setText("Paused");
                sensorManager.unregisterListener(MainActivity.this);
                BtnStop.setVisibility(View.INVISIBLE);
                BtnStart.setVisibility(View.VISIBLE);
                if (isPedometerSensorPresent == false) {
                    BtnReset.setVisibility(View.VISIBLE);
                } else {
                    BtnReset.setVisibility(View.INVISIBLE);
                }
            }
        });

        BtnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                TvSteps.setText("Let's Start!");
                CalorieView.setText("");
                sensorManager.unregisterListener(MainActivity.this);
                numSteps = 0;
                Calories = 0;
                editor.putLong("numSteps", 0);
                editor.putFloat("Calories", 0);
                editor.apply();
                BtnReset.setVisibility(View.INVISIBLE);
                BtnStop.setVisibility(View.INVISIBLE);
                BtnStart.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, "Records cleared", Toast.LENGTH_SHORT).show();
            }
        });

        addWater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                waterGlasses++;
                currentWaterQuantity = currentWaterQuantity + 250;
                editor.putInt("waterGlasses", waterGlasses);
                editor.putInt("currentWaterQuantity", currentWaterQuantity);
                editor.apply();
                currentWaterValue.setText("" + waterGlasses);
                waterQuantity.setText("( " + currentWaterQuantity + " ml" + " )");
            }
        });

        addCaffeine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                caffeineCups++;
                currentCaffeineQuantity = currentCaffeineQuantity + 80;
                editor.putInt("caffeineCups", caffeineCups);
                editor.putInt("currentCaffeineQuantity", currentCaffeineQuantity);
                editor.apply();
                currentCaffeineValue.setText("" + caffeineCups);
                caffeineQuantity.setText("( " + currentCaffeineQuantity + " mg" + " )");
            }
        });


        //Handling heart rate activities visibility, this chunk of code must exist in each activity!
        SensorManager mSensorManager;
        Menu nav_Menu = navigationView.getMenu();
        mSensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE) != null) {
            nav_Menu.findItem(R.id.nav_heart_rate).setVisible(true);
            HeartRateSensorIsPresent = true;

        } else {
            nav_Menu.findItem(R.id.nav_heart_rate_camera).setVisible(true);
            HeartRateSensorIsPresent = false;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, 0);
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.BODY_SENSORS) == PackageManager.PERMISSION_GRANTED) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BODY_SENSORS}, 1);
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
            MainActivity.this.moveTaskToBack(true);
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_home) {
        } else if (id == R.id.nav_news) {
            startActivity(i1);
        } else if (id == R.id.nav_about) {
            startActivity(i2);
        } else if (id == R.id.nav_settings) {
            startActivity(i3);
        } else if (id == R.id.nav_calculator) {
            startActivity(i4);
        } else if (id == R.id.nav_heart_rate) {
            startActivity(i5);
        } else if (id == R.id.nav_heart_rate_camera) {
            startActivity(i6);
        } else if (id == R.id.nav_hearing_wellbeing) {
            startActivity(i7);
        } else if (id == R.id.nav_videos) {
            startActivity(i8);
        } else if (id == R.id.nav_diet_manager_root) {
            startActivity(i9);
        } else if (id == R.id.nav_reminder) {
            startActivity(i10);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (isPedometerSensorPresent == false) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                simpleStepDetector.updateAccel(
                        event.timestamp, event.values[0], event.values[1], event.values[2], SensorSensitivityTemp);
            }
        } else {
            String rawCount = String.valueOf(event.values[0]);
            String actualCount = rawCount.substring(0, rawCount.length() - 2);
            Calories = Float.parseFloat(actualCount) / 20; //Algorithm by "Shape Up America!"
            numSteps = Long.parseLong(actualCount);
            TvSteps.setText(actualCount);
            stepsInCircle.setText(numSteps + "/10000");
            circularProgressbar.setProgress((int) numSteps);
            CalorieView.setText(Calories + " cal");
            editor.putLong("numSteps", numSteps);
            editor.putFloat("Calories", Calories);
            editor.apply();
        }
    }

    @Override
    public void step(long timeNs) {
        if (isPedometerSensorPresent == false) {
            numSteps++; //Stores value of steps
            Calories = numSteps / (float) 20; //Stores calories, algorithm by "Shape Up America!"
            TvSteps.setText("" + numSteps);
            stepsInCircle.setText(numSteps+"/10000");
            circularProgressbar.setProgress((int) numSteps);
            CalorieView.setText(Float.toString(Calories) + " cal");
            editor.putLong("numSteps", numSteps);
            editor.putFloat("Calories", Calories);
            editor.apply();
        }
    }

    public void launch_heart_activity_from_home(View view) {
        if (HeartRateSensorIsPresent == true) {
            startActivity(i5);
        } else {
            startActivity(i6);
        }
    }


    public void clear_fluids(View view) {
        waterGlasses = 0;
            currentWaterQuantity = 0;
            caffeineCups = 0;
            currentCaffeineQuantity = 0;
            editor.putInt("waterGlasses", waterGlasses);
            editor.putInt("currentWaterQuantity", currentWaterQuantity);
            editor.putInt("caffeineCups", caffeineCups);
            editor.putInt("currentCaffeineQuantity", currentCaffeineQuantity);
            editor.apply();
            currentWaterValue.setText("" + waterGlasses);
            waterQuantity.setText(currentWaterQuantity + " ml");
            currentCaffeineValue.setText("" + caffeineCups);
            caffeineQuantity.setText(currentCaffeineQuantity + " mg");
            Toast.makeText(MainActivity.this, "Records cleared", Toast.LENGTH_SHORT).show();
        }

}