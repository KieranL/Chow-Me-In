package com.chowpals.chowmein;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;
import java.util.List;

import interfaces.ChowMeInService;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import objects.Chows;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchChowActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SearchView chowSearchView;
    ListView chowSearchResults;
    ArrayList<Chows> chowsListed;
    ArrayList<Chows> masterChowList;

    private static final String BASE_URL = "https://api.chowme-in.com"; //Previous working API location: "http://chowmein.ca-central-1.elasticbeanstalk.com";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_chow);
        if (!networkConnectionAvailable())
            Toast.makeText(this, "You are not connected to the Internet. To send and receive Chows please connect to the Internet", Toast.LENGTH_SHORT).show();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initVariables();
        prepopulateList();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void getResultsAdapter(CharSequence query) {
        ArrayList<String> searchResultList = new ArrayList<>();
        ArrayList<Chows> temp = new ArrayList<>();
        chowsListed = masterChowList;
        for (int i = 0; i < chowsListed.size(); i++) {
            if (chowsListed.get(i).getFood().contains(query)) {
                searchResultList.add(chowsListed.get(i).getFood());
                temp.add(chowsListed.get(i));
            }
        }
        chowsListed = temp;
        ArrayAdapter<? extends String> resultAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, searchResultList);
        chowSearchResults.setAdapter(resultAdapter);
    }

    private void prepopulateList() {
        ArrayList<String> searchResultList = new ArrayList<>();
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create());

        Retrofit retrofit = builder.build();
        ChowMeInService apiClient = retrofit.create(ChowMeInService.class);

        apiClient.listChows().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe((List<Chows> response) -> {
                    for (Chows currentChow : response) {
                        currentChow = (verifyChow(currentChow));
                        chowsListed.add(currentChow);
                        searchResultList.add(currentChow.getFood());
                    }
                    masterChowList = chowsListed;
                    ArrayAdapter<? extends String> resultAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, searchResultList);
                    chowSearchResults.setAdapter(resultAdapter);
                }, error -> Log.i("error", "Error"));
    }


    private static Chows verifyChow(Chows currentChow) {
        if (currentChow.getCreatedTime() == null) {
            currentChow.setCreatedTime("");
        }

        if (currentChow.getFood() == null) {
            currentChow.setFood("");
        }

        if (currentChow.getLastUpdated() == null) {
            currentChow.setLastUpdated("");
        }

        if (currentChow.getMeetLocation() == null) {
            currentChow.setMeetLocation("");
        }

        if (currentChow.getMeetTime() == null) {
            currentChow.setMeetTime("");
        }

        if (currentChow.getNotes() == null) {
            currentChow.setNotes("");
        }

        if (currentChow.getCategory() == null) {
            currentChow.setCategory("None");
        }

        if (currentChow.getPosterPhone() == null) {
            currentChow.setPosterPhone("");
        }

        if (currentChow.getPosterName() == null) {
            currentChow.setPosterName("");
        }

        if (currentChow.getPosterUser() == null) {
            currentChow.setPosterUser("");
        }
        return currentChow;
    }

    private void initVariables() {
        chowsListed = new ArrayList<>();
        masterChowList = new ArrayList<>();
        chowSearchView = findViewById(R.id.chowSearchView);
        chowSearchResults = findViewById(R.id.chowSearchResults);
        chowSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                getResultsAdapter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                getResultsAdapter(s);
                return false;
            }
        });
        chowSearchResults.setOnItemClickListener((adapterView, view, i, l) -> {
            Chows selectedChow = chowsListed.get(i);
            viewChow(selectedChow);
        });
    }

    private void viewChow(Chows selectedChow) {
        Intent viewSelectedChow = new Intent(this, ViewChowActivity.class);
        viewSelectedChow.putExtra("Selected Chow", selectedChow);
        startActivity(viewSelectedChow);
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
        } else if (id == R.id.nav_create_chow) {
            startActivity(new Intent(this, CreateChowActivity.class));
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean networkConnectionAvailable() {
        ConnectivityManager connectionManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectionManager != null;
        NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

}
