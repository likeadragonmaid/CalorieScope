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
import android.widget.ImageButton;
import android.widget.TextView;

import org.dynamicsoft.caloriescope.R;
import org.dynamicsoft.caloriescope.activities.DietManagerActivity;

public class DrinksActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ImageButton dr1, dr2, dr3, dr4, dr5, dr6, dr7, dr8, dr9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_manager_drinks);
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

        dr1 = (ImageButton) findViewById(R.id.d1);
        dr2 = (ImageButton) findViewById(R.id.d2);
        dr3 = (ImageButton) findViewById(R.id.d3);
        dr4 = (ImageButton) findViewById(R.id.d4);
        dr5 = (ImageButton) findViewById(R.id.d5);
        dr6 = (ImageButton) findViewById(R.id.d6);
        dr7 = (ImageButton) findViewById(R.id.d7);
        dr8 = (ImageButton) findViewById(R.id.d8);
        dr9 = (ImageButton) findViewById(R.id.d9);

        Intent i;
        dr1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DrinksActivity.this, DrinksDetailActivity.class);
                intent.putExtra("Key", "1");
                startActivity(intent);

            }
        });

        dr2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DrinksActivity.this, DrinksDetailActivity.class);
                intent.putExtra("Key", "2");
                startActivity(intent);

            }
        });

        dr3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DrinksActivity.this, DrinksDetailActivity.class);
                intent.putExtra("Key", "3");
                startActivity(intent);

            }
        });

        dr4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DrinksActivity.this, DrinksDetailActivity.class);
                intent.putExtra("Key", "4");
                startActivity(intent);

            }
        });

        dr5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DrinksActivity.this, DrinksDetailActivity.class);
                intent.putExtra("Key", "5");
                startActivity(intent);
            }
        });

        dr6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DrinksActivity.this, DrinksDetailActivity.class);
                intent.putExtra("Key", "6");
                startActivity(intent);
            }
        });

        dr7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DrinksActivity.this, DrinksDetailActivity.class);
                intent.putExtra("Key", "7");
                startActivity(intent);
            }
        });

        dr8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DrinksActivity.this, DrinksDetailActivity.class);
                intent.putExtra("Key", "8");
                startActivity(intent);
            }
        });

        dr9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DrinksActivity.this, DrinksDetailActivity.class);
                intent.putExtra("Key", "9");
                startActivity(intent);
            }
        });
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
            DrinksActivity.this.moveTaskToBack(true);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_diet_manager_home) {
            Intent i = new Intent(DrinksActivity.this, DietManagerActivity.class);
            startActivity(i);
            finish();
        } else if (id == R.id.nav_food_suggestions) {
            Intent i = new Intent(DrinksActivity.this, FoodSuggestionsActivity.class);
            startActivity(i);
            finish();
        } else if (id == R.id.nav_exercise) {
            Intent i = new Intent(DrinksActivity.this, ExerciseActivity.class);
            startActivity(i);
            finish();
        } else if (id == R.id.nav_fat_burning_drinks) {
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}