package org.dynamicsoft.caloriescope;

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

import static org.dynamicsoft.caloriescope.MainActivity.i1;
import static org.dynamicsoft.caloriescope.MainActivity.i2;
import static org.dynamicsoft.caloriescope.MainActivity.i3;

public class CalculatorActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView mTextMessage, result_textview;
    private float weight, height, waist, hip, result;
    private Button calculate;
    private EditText weight_waist, height_hip;
    private int state = 0;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
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
                    return true;
                case R.id.navigation_whr:
                    state = 1;
                    weight_waist.setHint("Waist (inch)");
                    height_hip.setHint("Hip (inch)");
                    weight_waist.setText(null);
                    height_hip.setText(null);
                    result_textview.setText(null);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator_with_drawer);

        mTextMessage = findViewById(R.id.message);
        result_textview = findViewById(R.id.result_textview);
        weight_waist = findViewById(R.id.weight_waist);
        height_hip = findViewById(R.id.height_hip);
        calculate = findViewById(R.id.calculate);

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (state == 0) {
                    weight = Float.valueOf(weight_waist.getText().toString());
                    height = Float.valueOf(height_hip.getText().toString());
                    result = weight / (height * height);
                    result_textview.setText("BMI: " + String.valueOf(result));
                }
                if (state == 1) {
                    waist = Float.valueOf(weight_waist.getText().toString());
                    hip = Float.valueOf(height_hip.getText().toString());
                    result = waist / hip;
                    result_textview.setText("WHR: " + String.valueOf(result));
                }
            }
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
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
            CalculatorActivity.this.moveTaskToBack(true);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_home) {
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            super.onBackPressed();
            //startActivity(i0);finish();
        } else if (id == R.id.nav_medicalnews) {
            startActivity(i1);
            finish();
        } else if (id == R.id.nav_about) {
            startActivity(i2);
            finish();
        } else if (id == R.id.nav_settings) {
            startActivity(i3);
            finish();
        } else if (id == R.id.nav_calculator) {
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
}
