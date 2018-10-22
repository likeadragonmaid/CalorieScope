package org.dynamicsoft.caloriescope.activities;

import android.Manifest;
import android.content.Context;
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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.dynamicsoft.caloriescope.R;

import static org.dynamicsoft.caloriescope.activities.MainActivity.i1;
import static org.dynamicsoft.caloriescope.activities.MainActivity.i2;
import static org.dynamicsoft.caloriescope.activities.MainActivity.i3;
import static org.dynamicsoft.caloriescope.activities.MainActivity.i4;
import static org.dynamicsoft.caloriescope.activities.MainActivity.i7;

public class HeartRateSensorActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SensorEventListener {

    TextView HeartRateTxt;
    boolean isSensorPresent = false;
    private SensorManager mSensorManager;
    private Sensor mSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_rate_with_drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        HeartRateTxt = findViewById(R.id.HeartRateTxt);

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.BODY_SENSORS) == PackageManager.PERMISSION_GRANTED) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BODY_SENSORS}, 1);
            }
        }

        mSensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);

        if (mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE) != null) {
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
            mSensorManager.registerListener(this, mSensor, 3);
            isSensorPresent = true;
        } else {
            HeartRateTxt.setText("Heart rate sensor is not present!");
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Handling heart rate activities visibility, this is a variant of similar code in other activities.
        Menu nav_Menu = navigationView.getMenu();
        if (isSensorPresent == true) {
            nav_Menu.findItem(R.id.nav_heart_rate).setVisible(true);
        } else {
            nav_Menu.findItem(R.id.nav_heart_rate_camera).setVisible(true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isSensorPresent == true) {
            mSensorManager.registerListener(this, mSensor, 3);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isSensorPresent == true) {
            mSensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (isSensorPresent == true) {
            if ((int) event.values[0] != 0) {
                HeartRateTxt.setText("Current heart rate: " + Math.round(event.values[0]) + " BPM");
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
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
            HeartRateSensorActivity.this.moveTaskToBack(true);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

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
        } else if (id == R.id.nav_hearing_wellbeing) {
            startActivity(i7);
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
}