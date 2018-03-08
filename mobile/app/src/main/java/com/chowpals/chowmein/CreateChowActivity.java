package com.chowpals.chowmein;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import business.ChowHandler;
import objects.Chows;

public class CreateChowActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    EditText chowPostNameEditText;
    EditText chowPostLocationEditText;
    DatePicker chowPostDatePicker;
    TimePicker chowPostTimePicker;
    EditText chowPostDescriptionEditText;
    Button createChowButton;
    Spinner categorySpinner;


    @RequiresApi(api = Build.VERSION_CODES.M)
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initVariables() {
        chowPostNameEditText = findViewById(R.id.chowPostNameEditText);
        chowPostLocationEditText = findViewById(R.id.chowPostLocationEditText);
        chowPostTimePicker = findViewById(R.id.chowPostTimePicker);
        chowPostDatePicker = findViewById(R.id.chowPostDatePicker);
        chowPostDescriptionEditText = findViewById(R.id.chowPostDescriptionEditText);
        createChowButton = findViewById(R.id.createChowButton);
        categorySpinner = findViewById(R.id.categorySpinner);
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ChowHandler.getAllCategories());
        categorySpinner.setAdapter(categoryAdapter);
        createChowButton.setOnClickListener(view -> {
            boolean noInputErrors = false;
            if (!String.valueOf(chowPostNameEditText.getText()).equals("")
                    && !String.valueOf(chowPostDescriptionEditText.getText()).equals("")
                    && !String.valueOf(chowPostLocationEditText.getText()).equals(""))
                noInputErrors = true;
            if (noInputErrors) {
                ArrayList<String> categories = ChowHandler.getAllCategories();
                Chows newChow = new Chows(-1, "Mobile Guest", String.valueOf(new Date()), false,
                        String.valueOf(chowPostNameEditText.getText()).trim(), String.valueOf(new Date()),
                        String.valueOf(chowPostLocationEditText.getText()).trim(),
                        String.valueOf(chowPostDatePicker.getYear() + "-" + chowPostDatePicker.getMonth() + "-" + chowPostDatePicker.getDayOfMonth()
                                + " " + chowPostTimePicker.getHour() + ":" + chowPostTimePicker.getMinute()),
                        String.valueOf(chowPostDescriptionEditText.getText()).trim(), categories.get(categorySpinner.getSelectedItemPosition()), "Mobile Guest", "Mobile Guest User", "Poster has no phonenumber");
                Intent verifyChow = new Intent(this, VerifyChowActivity.class);
                verifyChow.putExtra("Chow", newChow);
                startActivity(verifyChow);
            } else {
                Toast.makeText(this, "Your Chow seems to be missing some information. Please fill out the empty fields.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        startActivity(new Intent(this,MainActivity.class));
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
