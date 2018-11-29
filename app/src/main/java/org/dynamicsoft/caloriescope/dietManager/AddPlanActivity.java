package org.dynamicsoft.caloriescope.dietManager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.dynamicsoft.caloriescope.R;
import org.dynamicsoft.caloriescope.activities.DietManagerActivity;

import java.util.ArrayList;
import java.util.List;

public class AddPlanActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private String Text;
    private DietManagerDBHelper mdatabasehelper;
    private Spinner spinner;
    private EditText fname, findi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_manager_diet_plan_add_plan);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        spinner = findViewById(R.id.spinner);
        fname = findViewById(R.id.foodname);
        findi = findViewById(R.id.indi);
        Button button = findViewById(R.id.add);

        mdatabasehelper = new DietManagerDBHelper(this);

        List<String> type = new ArrayList<>();
        type.add("Select Type");
        type.add("Breakfast");
        type.add("Lunch");
        type.add("Snacks");
        type.add("Dinner");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, type);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Text = spinner.getSelectedItem().toString();
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Intent intent = getIntent();
        final String m = intent.getStringExtra("Key");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Text != "Select Type" && fname.getText().toString() != "" && fname.getText().length() != 0) {
                    String name = fname.getText().toString();
                    String indi = findi.getText().toString();
                    AddData(m, name, indi, Text);
                }
            }
        });

    }

    private void AddData(String m, String name, String indi, String text) {
        boolean insertdata = mdatabasehelper.adddata(m, name, indi, text);
        if (insertdata) {
            Toast.makeText(getApplicationContext(), "Item added successfully ", Toast.LENGTH_LONG).show();
            Intent i = new Intent(AddPlanActivity.this, DietPlanDetailsActivity.class);
            i.putExtra("Key", m);
            startActivity(i);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Something went wrong ", Toast.LENGTH_LONG).show();
        }
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
            AddPlanActivity.this.moveTaskToBack(true);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Intent i;
        if (id == R.id.nav_diet_manager_home) {
            i = new Intent(AddPlanActivity.this, DietManagerActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_food_suggestions) {
            i = new Intent(AddPlanActivity.this, FoodSuggestionsActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_exercise) {
            i = new Intent(AddPlanActivity.this, ExerciseActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_fat_burning_drinks) {
            i = new Intent(AddPlanActivity.this, DrinksActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
