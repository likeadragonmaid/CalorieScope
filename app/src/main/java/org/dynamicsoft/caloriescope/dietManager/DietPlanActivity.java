package org.dynamicsoft.caloriescope.dietManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.dynamicsoft.caloriescope.R;
import org.dynamicsoft.caloriescope.activities.DietManagerActivity;

public class DietPlanActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_manager_diet_plan);
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
        SharedPreferences pref = getApplicationContext().getSharedPreferences("AppData", 0);
        TextView NavDrawerUserString = navigationView.getHeaderView(0).findViewById(R.id.NavDrawerUserString);
        NavDrawerUserString.setText(pref.getString("UserName", "Welcome"));

        CardView w1 = findViewById(R.id.w1);
        CardView w2 = findViewById(R.id.w2);
        CardView w3 = findViewById(R.id.w3);
        CardView w4 = findViewById(R.id.w4);
        CardView w5 = findViewById(R.id.w5);
        CardView w6 = findViewById(R.id.w6);
        CardView w7 = findViewById(R.id.w7);

        w1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DietPlanActivity.this, DietPlanDetailsActivity.class);

                i.putExtra("Key", "Monday");
                startActivity(i);

            }
        });
        w2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DietPlanActivity.this, DietPlanDetailsActivity.class);
                i.putExtra("Key", "Tuesday");
                startActivity(i);

            }
        });
        w3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DietPlanActivity.this, DietPlanDetailsActivity.class);
                i.putExtra("Key", "Wednesday");
                startActivity(i);
            }
        });
        w4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DietPlanActivity.this, DietPlanDetailsActivity.class);
                i.putExtra("Key", "Thursday");
                startActivity(i);
            }
        });

        w5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DietPlanActivity.this, DietPlanDetailsActivity.class);
                i.putExtra("Key", "Friday");
                startActivity(i);
            }
        });

        w6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DietPlanActivity.this, DietPlanDetailsActivity.class);
                i.putExtra("Key", "Saturday");
                startActivity(i);
            }
        });
        w7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DietPlanActivity.this, DietPlanDetailsActivity.class);
                i.putExtra("Key", "Sunday");
                startActivity(i);
            }
        });
    }

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
            DietPlanActivity.this.moveTaskToBack(true);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        Intent i;
        if (id == R.id.nav_diet_manager_home) {
            i = new Intent(DietPlanActivity.this, DietManagerActivity.class);
            startActivity(i);
            finish();
        } else if (id == R.id.nav_food_suggestions) {
            i = new Intent(DietPlanActivity.this, FoodSuggestionsActivity.class);
            startActivity(i);
            finish();
        } else if (id == R.id.nav_exercise) {
            i = new Intent(DietPlanActivity.this, ExerciseActivity.class);
            startActivity(i);
            finish();
        } else if (id == R.id.nav_fat_burning_drinks) {
            i = new Intent(DietPlanActivity.this, DrinksActivity.class);
            startActivity(i);
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

