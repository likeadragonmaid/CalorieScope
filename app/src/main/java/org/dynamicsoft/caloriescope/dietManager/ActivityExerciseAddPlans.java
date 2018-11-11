package org.dynamicsoft.caloriescope.dietManager;

import android.app.AlarmManager;
import android.content.Intent;
import android.provider.AlarmClock;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import org.dynamicsoft.caloriescope.R;
import org.dynamicsoft.caloriescope.activities.ActivityDietManager;
import org.dynamicsoft.caloriescope.reminder.Alarm;

public class ActivityExerciseAddPlans extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Spinner Workout,Days,Time;
    private Button Save;
    private String WorkoutText,DaysText,TimeText;
    private DBHelper mdatabasehelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_add_plans);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mdatabasehelper = new DBHelper(this);

        Workout=(Spinner)findViewById(R.id.spinner_addplan);

        Days=(Spinner)findViewById(R.id.spinner_adddays);

        Time=(Spinner)findViewById(R.id.spinner_addtimezone);

        Time.setVisibility(View.INVISIBLE);

        Workout.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                WorkoutText=Workout.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {
                Toast.makeText(ActivityExerciseAddPlans.this, "Please Select Workout Plan", Toast.LENGTH_SHORT).show();
            }
        });
        Days.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                DaysText=Days.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(ActivityExerciseAddPlans.this, "Please Select Day", Toast.LENGTH_SHORT).show();

            }
        });

        Time.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TimeText=Time.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        Save=(Button)findViewById(R.id.saveplan);
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!WorkoutText.equals("Select Excercise") && !DaysText.equals("Select Day"))
                {
                    AddWorkoutPlan(WorkoutText,DaysText);
                }
                else {
                    Toast.makeText(ActivityExerciseAddPlans.this, "Please Select Workout Plan and Day", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void AddWorkoutPlan(String workoutText, String daysText) {

        boolean data = mdatabasehelper.workplan(workoutText,daysText);
        if (data) {
            Toast.makeText(getApplicationContext(), "Added successfully ", Toast.LENGTH_LONG).show();
           Intent intent=new Intent(ActivityExerciseAddPlans.this,ActivityExercisePlans.class);
           startActivity(intent);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Something went wrong ", Toast.LENGTH_LONG).show();
        }
    }

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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_diet_manager_home) {
            Intent i = new Intent(ActivityExerciseAddPlans.this, ActivityDietManager.class);
            startActivity(i);
            finish();
        } else if (id == R.id.nav_food_suggestions) {
            Intent i = new Intent(ActivityExerciseAddPlans.this, ActivityFoodSuggestions.class);
            startActivity(i);
            finish();
        } else if (id == R.id.nav_exercise) {
        } else if (id == R.id.nav_fat_burning_drinks) {
            Intent i = new Intent(ActivityExerciseAddPlans.this, ActivityDrinks.class);
            startActivity(i);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
