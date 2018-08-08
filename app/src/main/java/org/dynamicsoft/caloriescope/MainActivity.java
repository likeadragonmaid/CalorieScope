package org.dynamicsoft.caloriescope;

import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.dynamicsoft.caloriescope.StepDetector;
import org.dynamicsoft.caloriescope.StepListener;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SensorEventListener, StepListener {

    private static final String TEXT_NUM_STEPS = "Steps: ";
    public long numSteps;
    public int waterGlasses, caffeineCups, currentWaterQuantity, currentCaffeineQuantity;
    public float Calories, SensorSentivityTemp;
    public ImageButton addWater, addCaffeine;
    public SharedPreferences pref;
    public SharedPreferences.Editor editor;
    private StepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor accel;
    private Button BtnStart, BtnStop, BtnReset, ClearFluids;
    private TextView TvSteps, CalorieView, currentWaterValue, currentCaffeineValue, waterQuantity, caffeineQuantity;
    public static Intent i0,i1,i2,i3;
    public static Bundle webViewBundle = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer);
        i0 = new Intent(this, MainActivity.class);
        i1 = new Intent(this, MedicalNewsActivity.class);
        i2 = new Intent(this, AboutActivity.class);
        i3 = new Intent(this, SettingsActivity.class);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        editor = pref.edit();
        SensorSentivityTemp = pref.getFloat("CurrentSenstivityValue",30f);

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

        waterGlasses = pref.getInt("waterGlasses", 0);
        currentWaterQuantity = pref.getInt("currentWaterQuantity", 0);
        currentWaterValue.setText("" + waterGlasses);
        waterQuantity.setText(currentWaterQuantity + " ml");
        caffeineCups = pref.getInt("caffeineCups", 0);
        currentCaffeineQuantity = pref.getInt("currentCaffeineQuantity", 0);
        currentCaffeineValue.setText("" + caffeineCups);
        caffeineQuantity.setText(currentCaffeineQuantity + " mg");

        numSteps = pref.getLong("numSteps", 0);
        Calories = pref.getFloat("Calories", 0);

        // Get an instance of the SensorManager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(this);

        BtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                sensorManager.registerListener(MainActivity.this, accel, SensorManager.SENSOR_DELAY_FASTEST);
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
                BtnReset.setVisibility(View.VISIBLE);
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
                waterQuantity.setText(currentWaterQuantity + " ml");
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
                caffeineQuantity.setText(currentCaffeineQuantity + " mg");
            }
        });

        ClearFluids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
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
        });
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
        getMenuInflater().inflate(R.menu.menu_about, menu);
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
        } else if (id == R.id.nav_medicalnews) {
            startActivity(i1);
        } else if (id == R.id.nav_about) {
            startActivity(i2);
        } else if (id == R.id.nav_settings) {
            startActivity(i3);
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
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector.updateAccel(
                    event.timestamp, event.values[0], event.values[1], event.values[2],SensorSentivityTemp);
        }
    }

    @Override
    public void step(long timeNs) {
        numSteps++; //Stores value of steps
        Calories = numSteps / (float) 20; //Stores calories, algorithm by "Shape Up America!"
        TvSteps.setText(TEXT_NUM_STEPS + numSteps);
        CalorieView.setText("Calories Burnt: " + Float.toString(Calories) + " cal");
        editor.putLong("numSteps", numSteps);
        editor.putFloat("Calories", Calories);
        editor.apply();
    }
}