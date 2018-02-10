package org.dynamicsoft.healthometer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
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
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;

import static org.dynamicsoft.healthometer.MainActivity.i0;
import static org.dynamicsoft.healthometer.MainActivity.i1;
import static org.dynamicsoft.healthometer.MainActivity.i2;

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

        SenstivityTextView.setText("Senstivity: " + (int) CurrentSenstivityValue);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            SenstivitySeekBar.setMax(101);
            SenstivitySeekBar.setMin(1);
        }   else{
                SenstivitySeekBar.setMax(100);
        }

        SenstivitySeekBar.setProgress( (int) CurrentSenstivityValue);

        SenstivitySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
                SenstivityTextView.setText("Senstivity: " + (int) progressChangedValue);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                SenstivityTextView.setText("Senstivity: " + (int) progressChangedValue);
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
                /*/Toast.makeText(getApplicationContext(), "Settings saved", Toast.LENGTH_LONG).show();
                Intent i = getBaseContext().getPackageManager()
                        .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);/*/
                Toast.makeText(getApplicationContext(), "This button is a placeholder right now", Toast.LENGTH_LONG).show();
            }
        });

        LoadDefaults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0){
                SenstivityTextView.setText("Senstivity: 30");
                CurrentSenstivityValue = 30f;
                editor.putFloat("CurrentSenstivityValue", CurrentSenstivityValue);
                editor.apply();
                Toast.makeText(getApplicationContext(), "Defaults Loaded", Toast.LENGTH_LONG).show();
                Intent i = getBaseContext().getPackageManager()
                        .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        ClearAppData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0){
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SettingsActivity.this);
                    alertDialog.setTitle("WARNING!");
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage("Are you sure, you want to clear app data?");
                    alertDialog.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int which) {
                                editor.putInt("waterGlasses", 0);
                                editor.putInt("currentWaterQuantity", 0);
                                editor.putInt("caffeineCups", 0);
                                editor.putInt("currentCaffeineQuantity", 0);
                                editor.putLong("numSteps", 0);
                                editor.putFloat("Calories", 0);
                                editor.putFloat("CurrentSenstivityValue", 30f);
                                editor.apply();
                                Toast.makeText(getApplicationContext(), "App Data Cleared", Toast.LENGTH_LONG).show();
                                Intent i = getBaseContext().getPackageManager()
                                        .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                            }
                        });
                    alertDialog.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,	int which) {
                                dialog.cancel();
                            }
                        });
                    alertDialog.show();
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

        int id = item.getItemId();

        if (id == R.id.nav_home) {
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            super.onBackPressed();
            //startActivity(i0);finish();
        } else if (id == R.id.nav_medicalnews) {
            startActivity(i1);finish();
        } else if (id == R.id.nav_about) {
            startActivity(i2);finish();
        } else if (id == R.id.nav_settings) {
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        finish();
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        return super.onKeyDown(keyCode, event);
    }
}