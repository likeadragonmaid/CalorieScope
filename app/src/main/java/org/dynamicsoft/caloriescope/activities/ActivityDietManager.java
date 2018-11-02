package org.dynamicsoft.caloriescope.activities;

import android.content.Intent;
import android.os.Bundle;
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
import android.widget.ImageButton;

import org.dynamicsoft.caloriescope.R;
import org.dynamicsoft.caloriescope.dietManager.ActivityDrinks;
import org.dynamicsoft.caloriescope.dietManager.ActivityExercise;
import org.dynamicsoft.caloriescope.dietManager.ActivityFoodSuggestions;
import org.dynamicsoft.caloriescope.dietManager.ActivityDietPlan;

public class ActivityDietManager extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ImageButton img1, img2, img3, img4;
    private CardView cardView1, cardView2, cardView3, cardView4;
    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_manager_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        cardView1 = (CardView) findViewById(R.id.card1);
        cardView2 = (CardView) findViewById(R.id.card2);
        cardView3 = (CardView) findViewById(R.id.card3);
        cardView4 = (CardView) findViewById(R.id.card4);

        img1 = (ImageButton) findViewById(R.id.diet_plan);
        img2 = (ImageButton) findViewById(R.id.sugges);
        img3 = (ImageButton) findViewById(R.id.simpleexe);
        img4 = (ImageButton) findViewById(R.id.fatdrink);

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(ActivityDietManager.this, ActivityDietPlan.class);
                startActivity(i);
            }
        });


        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(ActivityDietManager.this, ActivityFoodSuggestions.class);
                startActivity(i);
            }
        });


        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(ActivityDietManager.this, ActivityExercise.class);
                startActivity(i);
            }
        });

        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(ActivityDietManager.this, ActivityDrinks.class);
                startActivity(i);
            }
        });

        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(ActivityDietManager.this, ActivityFoodSuggestions.class);
                startActivity(i);
                }
        });

        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(ActivityDietManager.this, ActivityExercise.class);
                startActivity(i);
            }
        });

        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(ActivityDietManager.this, ActivityDrinks.class);
                startActivity(i);
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
        } else if (id == R.id.nav_food_suggestions) {
            i = new Intent(ActivityDietManager.this, ActivityFoodSuggestions.class);
            startActivity(i);
        } else if (id == R.id.nav_exercise) {
            i = new Intent(ActivityDietManager.this, ActivityExercise.class);
            startActivity(i);
        } else if (id == R.id.nav_fat_burning_drinks) {
            i = new Intent(ActivityDietManager.this, ActivityDrinks.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}