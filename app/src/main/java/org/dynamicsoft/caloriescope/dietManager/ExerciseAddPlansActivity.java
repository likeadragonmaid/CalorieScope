package org.dynamicsoft.caloriescope.dietManager;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.dynamicsoft.caloriescope.R;
import org.dynamicsoft.caloriescope.activities.DietManagerActivity;

public class ExerciseAddPlansActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Spinner Workout, Days, Time;
    private Button Save;
    private String WorkoutText, DaysText, TimeText;
    private DietManagerDBHelper mdatabasehelper;

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

        //To set Person's name in Nav Drawer
        SharedPreferences pref = getApplicationContext().getSharedPreferences("AppData", 0);
        TextView NavDrawerUserString = navigationView.getHeaderView(0).findViewById(R.id.NavDrawerUserString);
        NavDrawerUserString.setText(pref.getString("UserName", "Welcome"));

        mdatabasehelper = new DietManagerDBHelper(this);

        Workout = (Spinner) findViewById(R.id.spinner_addplan);

        Days = (Spinner) findViewById(R.id.spinner_adddays);

        Time = (Spinner) findViewById(R.id.spinner_addtimezone);

        Time.setVisibility(View.INVISIBLE);

        Workout.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                WorkoutText = Workout.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(ExerciseAddPlansActivity.this, "Please Select Workout Plan", Toast.LENGTH_SHORT).show();
            }
        });
        Days.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                DaysText = Days.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(ExerciseAddPlansActivity.this, "Please Select Day", Toast.LENGTH_SHORT).show();

            }
        });

        Time.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TimeText = Time.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        Save = (Button) findViewById(R.id.saveplan);
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!WorkoutText.equals("Select Excercise") && !DaysText.equals("Select Day")) {
                    AddWorkoutPlan(WorkoutText, DaysText);
                } else {
                    Toast.makeText(ExerciseAddPlansActivity.this, "Please Select Workout Plan and Day", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void AddWorkoutPlan(String workoutText, String daysText) {

        boolean data = mdatabasehelper.workplan(workoutText, daysText);
        if (data) {
            Toast.makeText(getApplicationContext(), "Added successfully ", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(ExerciseAddPlansActivity.this, ExercisePlansActivity.class);
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
            ExerciseAddPlansActivity.this.moveTaskToBack(true);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_diet_manager_home) {
            Intent i = new Intent(ExerciseAddPlansActivity.this, DietManagerActivity.class);
            startActivity(i);
            finish();
        } else if (id == R.id.nav_food_suggestions) {
            Intent i = new Intent(ExerciseAddPlansActivity.this, FoodSuggestionsActivity.class);
            startActivity(i);
            finish();
        } else if (id == R.id.nav_exercise) {
        } else if (id == R.id.nav_fat_burning_drinks) {
            Intent i = new Intent(ExerciseAddPlansActivity.this, DrinksActivity.class);
            startActivity(i);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
