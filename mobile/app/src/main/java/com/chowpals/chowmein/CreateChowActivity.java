package com.chowpals.chowmein;

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
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;

import objects.Chows;

public class CreateChowActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    EditText chowPostNameEditText;
    EditText chowPostLocationEditText;
    EditText chowPostTimeEditText;
    EditText chowPostDescriptionEditText;
    Button createChowButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_chow);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initVariables();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initVariables() {
        chowPostNameEditText = findViewById(R.id.chowPostNameEditText);
        chowPostLocationEditText = findViewById(R.id.chowPostLocationEditText);
        chowPostTimeEditText = findViewById(R.id.chowPostTimeEditText);
        chowPostDescriptionEditText = findViewById(R.id.chowPostDescriptionEditText);
        createChowButton = findViewById(R.id.createChowButton);
        createChowButton.setOnClickListener(view -> {
            boolean noInputErrors = false;
            if (!String.valueOf(chowPostNameEditText.getText()).equals(""))
                noInputErrors = true;
            if (!String.valueOf(chowPostDescriptionEditText.getText()).equals(""))
                noInputErrors = true;
            if (noInputErrors) {
                Chows newChow = new Chows(1, 1, String.valueOf(new Date()), false,
                        String.valueOf(chowPostNameEditText.getText()), String.valueOf(new Date()),
                        String.valueOf(chowPostLocationEditText.getText()),
                        String.valueOf(chowPostTimeEditText.getText()),
                        String.valueOf(chowPostDescriptionEditText.getText()));
            }
        });
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_chow, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            startActivity(new Intent(this, MainActivity.class));
        } else if (id == R.id.nav_search_chow) {
            startActivity(new Intent(this, SearchChowActivity.class));
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
