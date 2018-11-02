package org.dynamicsoft.caloriescope.dietManager;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import org.dynamicsoft.caloriescope.R;
import org.dynamicsoft.caloriescope.activities.ActivityDietManager;

public class ActivityDrinksDetail extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private WebView wx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_manager_drinks_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        wx = (WebView) findViewById(R.id.exc);
        wx.getSettings().setJavaScriptEnabled(true);

        Intent intent = getIntent();
        String message = intent.getStringExtra("Key");


        if (message.equals("1")) {
            wx.loadUrl("file:///android_asset/drinksdet/first.html");
        }
        if (message.equals("2")) {
            wx.loadUrl("file:///android_asset/drinksdet/two.html");
        }
        if (message.equals("3")) {
            wx.loadUrl("file:///android_asset/drinksdet/third.html");
        }
        if (message.equals("4")) {
            wx.loadUrl("file:///android_asset/drinksdet/four.html");
        }
        if (message.equals("5")) {
            wx.loadUrl("file:///android_asset/drinksdet/five.html");
        }
        if (message.equals("6")) {
            wx.loadUrl("file:///android_asset/drinksdet/six.html");
        }
        if (message.equals("7")) {
            wx.loadUrl("file:///android_asset/drinksdet/sev.html");
        }
        if (message.equals("8")) {
            wx.loadUrl("file:///android_asset/drinksdet/eit.html");
        }
        if (message.equals("9")) {
            wx.loadUrl("file:///android_asset/drinksdet/nine.html");

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

        Intent i;
        if (id == R.id.nav_diet_manager_home) {
            i = new Intent(ActivityDrinksDetail.this, ActivityDietManager.class);
            startActivity(i);
            finish();
        } else if (id == R.id.nav_food_suggestions) {
            i = new Intent(ActivityDrinksDetail.this, ActivityFoodSuggestions.class);
            startActivity(i);
            finish();
        } else if (id == R.id.nav_exercise) {
            i = new Intent(ActivityDrinksDetail.this, ActivityExercise.class);
            startActivity(i);
            finish();
        } else if (id == R.id.nav_fat_burning_drinks) {
            i = new Intent(ActivityDrinksDetail.this, ActivityDrinks.class);
            startActivity(i);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}