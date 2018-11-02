package org.dynamicsoft.caloriescope.dietManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.ListView;

import org.dynamicsoft.caloriescope.R;
import org.dynamicsoft.caloriescope.activities.ActivityDietManager;

import java.util.ArrayList;

public class ActivityDietPlanDetails extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ListView list;
    DBHelper mdatabasehelper;
    ArrayList<String> listdata = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> id = new ArrayList<>();
    private FloatingActionButton fab;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_manager_plan_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();
        key = intent.getStringExtra("Key");


        list = findViewById(R.id.list);
        fab = findViewById(R.id.donefab);
        mdatabasehelper = new DBHelper(this);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ActivityDietPlanDetails.this, ActivityAddPlan.class);
                i.putExtra("Key", key);
                startActivity(i);
                finish();
            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                removeItemFromList(i);
                return true;
            }
        });

        popuplist(key);

    }

    protected void removeItemFromList(int i) {
        final int deletePostion = i;
        AlertDialog.Builder alert = new AlertDialog.Builder(ActivityDietPlanDetails.this);
        alert.setTitle("Delete?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (deletePostion > -1) {
                    mdatabasehelper.deletex(String.valueOf(id.get(deletePostion)));
                    listdata.remove(deletePostion);
                    arrayAdapter.notifyDataSetChanged();
                    arrayAdapter.notifyDataSetInvalidated();
                }
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

            }
        });
        alert.show();

    }

    public void popuplist(final String key) {
        Cursor data = mdatabasehelper.getdata(key);
        while (data.moveToNext()) {
            listdata.add("\nType : " + data.getString(1) + "\n\n" + "Item : " + data.getString(3)
                    + "\n\n" + "Ingredients : " + data.getString(4));
            id.add(data.getString(0));
        }

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listdata);
        list.setAdapter(arrayAdapter);


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
        Intent i;
        if (id == R.id.nav_diet_manager_home) {
            i = new Intent(ActivityDietPlanDetails.this, ActivityDietManager.class);
            startActivity(i);
        } else if (id == R.id.nav_food_suggestions) {
            i = new Intent(ActivityDietPlanDetails.this, ActivityFoodSuggestions.class);
            startActivity(i);
        } else if (id == R.id.nav_exercise) {
            i = new Intent(ActivityDietPlanDetails.this, ActivityExercise.class);
            startActivity(i);
        } else if (id == R.id.nav_fat_burning_drinks) {
            i = new Intent(ActivityDietPlanDetails.this, ActivityDrinks.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
