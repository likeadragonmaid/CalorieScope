package org.dynamicsoft.healthometer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import android.widget.SeekBar;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public SeekBar SenstivitySeekBar;
    public TextView SenstivityTextView;
    public Button SaveSettings, LoadDefaults, ClearAppData;
    public int progressChangedValue = 0;
    public float CurrentSenstivityValue;
    public SharedPreferences pref;
    public SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_with_drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SenstivitySeekBar = findViewById(R.id.SenstivitySeekBar);
        SenstivityTextView = findViewById(R.id.SenstivityTextView);
        SaveSettings = findViewById(R.id.SaveSettings);
        LoadDefaults = findViewById(R.id.LoadDefaults);
        ClearAppData = findViewById(R.id.ClearAppData);

        pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        editor = pref.edit();
        CurrentSenstivityValue = pref.getFloat("CurrentSenstivityValue", 30f);

        SenstivityTextView.setText("Senstivity: "+CurrentSenstivityValue);
        SenstivitySeekBar.setMax(100);
        SenstivitySeekBar.setProgress((int)CurrentSenstivityValue);

        SenstivitySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
                SenstivityTextView.setText("Senstivity: "+progressChangedValue);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                SenstivityTextView.setText("Senstivity: "+progressChangedValue);
                CurrentSenstivityValue = (float) progressChangedValue;
                editor.putFloat("CurrentSenstivityValue", CurrentSenstivityValue);
                editor.apply();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SaveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0){
                Intent i = getBaseContext().getPackageManager()
                        .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        LoadDefaults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0){

                SenstivityTextView.setText("Senstivity: 30");
                CurrentSenstivityValue = 30f;
                editor.putFloat("CurrentSenstivityValue", CurrentSenstivityValue);
                editor.apply();

                Intent i = getBaseContext().getPackageManager()
                        .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        ClearAppData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0){

                editor.putInt("waterGlasses", 0);
                editor.putInt("currentWaterQuantity", 0);
                editor.putInt("caffeineCups", 0);
                editor.putInt("currentCaffeineQuantity", 0);
                editor.putLong("numSteps", 0);
                editor.putFloat("Calories", 0);

                editor.putFloat("CurrentSenstivityValue", 30f);
                editor.apply();

                Intent i = getBaseContext().getPackageManager()
                        .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
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
        getMenuInflater().inflate(R.menu.menu_about, menu);
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
    public boolean onNavigationItemSelected(MenuItem item) {
        Intent i0 = new Intent(this, MainActivity.class);
        Intent i1 = new Intent(this, MedicalNewsActivity.class);
        Intent i2 = new Intent(this, AboutActivity.class);
        Intent i3 = new Intent(this, SettingsActivity.class);

        int id = item.getItemId();

        if (id == R.id.nav_home) {
            SettingsActivity.this.finish();
            startActivity(i0);
        } else if (id == R.id.nav_medicalnews) {
            SettingsActivity.this.finish();
            startActivity(i1);
        } else if (id == R.id.nav_about) {
            SettingsActivity.this.finish();
            startActivity(i2);
        } else if (id == R.id.nav_settings) {
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        return super.onKeyDown(keyCode, event);
    }
}