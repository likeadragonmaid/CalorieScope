package org.dynamicsoft.caloriescope.dietManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.dynamicsoft.caloriescope.R;
import org.dynamicsoft.caloriescope.activities.DietManagerActivity;

import java.util.ArrayList;

public class DietPlanDetailsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private final ArrayList<String> listdata = new ArrayList<>();
    private final ArrayList<String> id = new ArrayList<>();
    private final int Menu_DELETE = 0;
    private ListView list;
    private DietManagerDBHelper mdatabasehelper;
    private ArrayAdapter<String> arrayAdapter;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_manager_plan_details);
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

        Intent intent = getIntent();
        key = intent.getStringExtra("Key");


        list = findViewById(R.id.list);
        FloatingActionButton fab = findViewById(R.id.donefab);
        mdatabasehelper = new DietManagerDBHelper(this);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DietPlanDetailsActivity.this, AddPlanActivity.class);
                i.putExtra("Key", key);
                startActivity(i);
                finish();
            }
        });

        registerForContextMenu(list);

        popuplist(key);

    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.list) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            menu.add(Menu.NONE, Menu_DELETE, Menu.NONE, "Delete");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = item.getItemId();

        if (index == Menu_DELETE) {
            int deletePost = info.position;
            if (deletePost > -1) {
                mdatabasehelper.deletex(String.valueOf(id.get(deletePost)));
                listdata.remove(deletePost);
                arrayAdapter.notifyDataSetChanged();
                arrayAdapter.notifyDataSetInvalidated();
            }
        } else {
            Toast.makeText(this, "Unable To Perform This Action", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    private void popuplist(final String key) {
        Cursor data = mdatabasehelper.getdata(key);
        while (data.moveToNext()) {
            listdata.add("\nType : " + data.getString(1) + "\n\n" + "Item : " + data.getString(3)
                    + "\n\n" + "Ingredients : " + data.getString(4));
            id.add(data.getString(0));
        }

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listdata);
        list.setAdapter(arrayAdapter);
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
            DietPlanDetailsActivity.this.moveTaskToBack(true);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Intent i;
        if (id == R.id.nav_diet_manager_home) {
            i = new Intent(DietPlanDetailsActivity.this, DietManagerActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_food_suggestions) {
            i = new Intent(DietPlanDetailsActivity.this, FoodSuggestionsActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_exercise) {
            i = new Intent(DietPlanDetailsActivity.this, ExerciseActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_fat_burning_drinks) {
            i = new Intent(DietPlanDetailsActivity.this, DrinksActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
